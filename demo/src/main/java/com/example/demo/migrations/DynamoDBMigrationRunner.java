package com.example.demo.migrations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class DynamoDBMigrationRunner implements CommandLineRunner {

    private final ApplicationContext applicationContext;
    private final MigrationRecorder migrationRecorder;

    public DynamoDBMigrationRunner(ApplicationContext applicationContext,MigrationRecorder migrationRecorder) {
        this.applicationContext = applicationContext;
        this.migrationRecorder = migrationRecorder;

    }

    @Override
    public void run(String... args) {
        System.out.println("🔹 Start running migration...");

        File migrationDir = new File("src/main/java/com/example/demo/migrations/");
        File[] migrationFiles = migrationDir.listFiles((dir, name) -> name.endsWith(".java"));

        if (migrationFiles == null || migrationFiles.length == 0) {
            System.out.println("⚠️ There are no migration files to run!");
            return;
        }

        List<File> sortedMigrations = Arrays.stream(migrationFiles)
                .sorted(Comparator.comparing(DynamoDBMigrationRunner::extractTimestampFromFileName))
                .toList();

        for (File file : sortedMigrations) {
            String migrationClassName = extractClassNameFromFile(file.getName());
            String beanName = convertToSpringBeanName(migrationClassName);

            if (migrationClassName.contains("DynamoDBMigrationRunner") || migrationClassName.contains("MigrationRecorder")) {
                System.out.println("⏩ Skip files: " + migrationClassName);
                continue;
            }

            try {
                Object migrationInstance = applicationContext.getBean(beanName);
                runAllMigrationMethods(migrationInstance);
                migrationRecorder.recordMigration(migrationClassName, "Success");
                System.out.println("✅ Migration run successfully: " + migrationClassName);
            } catch (Exception e) {
                System.err.println("❌ Error while running migration: " + migrationClassName);
                migrationRecorder.recordMigration(migrationClassName, "Error");

                e.printStackTrace();
            }
        }

        System.out.println("✅ Complete migration!");
    }

    private void runAllMigrationMethods(Object migrationInstance) {
        Method[] methods = migrationInstance.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterCount() == 0) { 
                try {
                    System.out.println("🚀 Run method: " + method.getName() + "()");
                    method.invoke(migrationInstance);
                } catch (Exception e) {
                    System.err.println("❌ Error while running migration: " + method.getName());
                    e.printStackTrace();
                }
            }
        }
    }


    private static String convertToSpringBeanName(String className) {
        return Character.toLowerCase(className.charAt(0)) + className.substring(1);
    }

    private static String extractClassNameFromFile(String fileName) {
        return fileName.replace(".java", "");
    }

    private static String extractTimestampFromFileName(File file) {
        String fileName = file.getName().replace(".java", "");
        String[] parts = fileName.split("_");
        return parts.length > 1 ? parts[parts.length - 1] : fileName;
    }
}
