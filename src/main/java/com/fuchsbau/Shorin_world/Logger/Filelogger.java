package com.fuchsbau.Shorin_world.Logger;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Filelogger {
    private static final Filelogger filelogger;
    private final String directory = "Logging";

    static {
        try {
            filelogger = new Filelogger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Logger logger;
    FileHandler handler;

    private Filelogger() throws IOException {
        logger = Logger.getLogger((Logger.GLOBAL_LOGGER_NAME));

        File directory = new File(this.directory);
        String message;
        if (!directory.exists()) {
            boolean isCreated = directory.mkdir();
            if (isCreated) {
                message = ("Directory created successfully");
            } else {
                message = ("Failed to create directory");
            }
        } else {
            message = ("Directory already exists");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String formattedDate = dateFormat.format(new Date());
        String fileName = "Shorin_log_" + formattedDate + ".txt";

        handler = new FileHandler(directory + "/" + fileName);
        logger.addHandler(handler);

        SimpleFormatter formatter = new CustomFormatter();
        handler.setFormatter(formatter);

        logger.info(message);
        logger.info("Logger Initialised");
        cleanup();
    }

    static class CustomFormatter extends SimpleFormatter {
        private final Date dat = new Date();
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

        @Override
        public String format(LogRecord record) {
            dat.setTime(record.getMillis());
            String source;
            if (record.getSourceClassName() != null) {
                source = record.getSourceClassName();
                if (record.getSourceMethodName() != null) {
                    source += " " + record.getSourceMethodName();
                }
            } else {
                source = record.getLoggerName();
            }
            String message = formatMessage(record);
            String throwable = "";
            if (record.getThrown() != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                pw.println();
                record.getThrown().printStackTrace(pw);
                pw.close();
                throwable = sw.toString();
            }
            return dateFormat.format(dat) + " " + source + ": " + message + "\n" + throwable;
        }
    }

    private void cleanup() {
        String directoryPath = directory + "/";

        // Calculate threshold date (10 days ago)
        long thresholdMillis = new Date().getTime() - 10 * 24 * 60 * 60 * 1000;
        Date thresholdDate = new Date(thresholdMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

        File[] files = new File(directoryPath).listFiles();

        if (files != null) {
            for (File file : files) {
                // Get the creation date of the file from its name
                String fileName = file.getName().split("_")[2];// Extract yyyyMMdd part from the filename
                fileName = fileName.substring(0, fileName.length() - 4);
                try {
                    Date fileCreationDate = dateFormat.parse(fileName);

                    if (fileCreationDate.before(thresholdDate)) {
                        if (file.delete()) {
                            logger.warning("File deleted: " + file.getName());
                        } else {
                            logger.warning("Failed to delete file: " + file.getName());
                        }
                    }
                } catch (ParseException e) {
                    logger.severe("Error parsing file creation date: " + e.getMessage());
                }
            }
        } else {
            logger.info("No files found in the directory.");
        }
    }


    private void closeLogger() {
        handler.close();
    }

    public static Filelogger getInstance() {
        return filelogger;
    }

    public static Logger getLogger() {
        return getInstance().logger;
    }
}
