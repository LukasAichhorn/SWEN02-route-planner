package at.fh.tourplanner.businessLayer.pdfGenerationService;

import at.fh.tourplanner.DataAccessLayer.DAO;
import at.fh.tourplanner.businessLayer.pdfGenerationService.PdfGenerationService;
import at.fh.tourplanner.configuration.AppConfiguration;
import at.fh.tourplanner.configuration.AppConfigurationLoader;
import at.fh.tourplanner.model.Log;
import at.fh.tourplanner.model.Tour;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
public class PdfGenerationServiceImpl implements PdfGenerationService {

    public static final String STATISTICAL_REPORT_PDF_NAME = "StatReport_";
    public static final String TOUR_REPORT_PDF_NAME = "TourReport_";
    public static final String FOLDER_PATH = AppConfigurationLoader.getInstance().getAppConfiguration().getReportFolder();

    private DAO database;

    public PdfGenerationServiceImpl(DAO instance) {

        this.database = instance;
    }

    @Override
    public void generateStatisticalReport() {
        String file = FOLDER_PATH + STATISTICAL_REPORT_PDF_NAME +  LocalDate.now() + "_" + UUID.randomUUID() + ".pdf";
        try{
        List<Tour> tours = database.getAllTours();
        for(var tour : tours ){
            tour.setLogs(database.getAllLogsForTour(tour.getPostgresID()));
        }
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(generateTourHeader("Statistical Report"));
            document.add(generateStatisticsTable(tours));
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Table generateStatisticsTable(List<Tour> tours) {
        Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Id"));
        table.addHeaderCell(getHeaderCell("Name"));
        table.addHeaderCell(getHeaderCell("Avg. time"));
        table.addHeaderCell(getHeaderCell("Avg. rating"));
        tours.forEach(tour -> {
            double avgTime = 0.0;
            double avgRating = 0.0;
            table.addCell(String.valueOf(tour.getPostgresID()));
            table.addCell(tour.getName());
            for (Log log : tour.getLogs()) {
                avgTime += log.getDuration();
                avgRating += log.getRating();
            }
            if (!tour.getLogs().isEmpty()) {
                table.addCell(String.valueOf(avgTime/tour.getLogs().size()));
                table.addCell(String.valueOf(avgRating/tour.getLogs().size()));
            }
            else {
                table.addCell("No logs");
                table.addCell("No logs");
            }
        });

        return table;
    }

    @Override
    public void generateTourReport(Tour tour) {
        String file = FOLDER_PATH + TOUR_REPORT_PDF_NAME +  tour.getName() + "_" + LocalDate.now() + ".pdf";
        try {
            tour.setLogs(database.getAllLogsForTour(tour.getPostgresID()));
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            try (Document document = new Document(pdf)) {
                Table t = generateTourTable(tour);
                ImageData data = ImageDataFactory.create(tour.getTourImagePath());
                document.add(generateTourHeader("Tour Report for " + tour.getName()));
                document.add(generateSectionHeader("Tour details"));
                document.add(t);
                document.add(generateSectionHeader("Description"));
                document.add(generateSectionText(tour.getDescription()));
                document.add(generateSectionHeader("Tour logs"));
                document.add(generateTableForTourLogs(tour));
                document.add(new AreaBreak());
                document.add(generateSectionHeader("Tour image"));
                document.add(new Image(data).setAutoScale(true));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Table generateTourTable(Tour tour) {
        Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Id"));
        table.addHeaderCell(getHeaderCell("Start"));
        table.addHeaderCell(getHeaderCell("Destination"));
        table.addHeaderCell(getHeaderCell("Transport Type"));
        table.addHeaderCell(getHeaderCell("Distance"));
        table.addHeaderCell(getHeaderCell("Estimated Time"));
        table.addCell(String.valueOf(tour.getPostgresID()));
        table.addCell(tour.getStart());
        table.addCell(tour.getDestination());
        table.addCell(String.valueOf(tour.getTransportType()));
        table.addCell(String.valueOf(tour.getDistance()));
        table.addCell(String.valueOf(tour.getEstimatedTime()));
        return table;
    }


    public Table generateTableForTourLogs(Tour tour) {
        Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Id"));
        table.addHeaderCell(getHeaderCell("Duration"));
        table.addHeaderCell(getHeaderCell("Difficulty"));
        table.addHeaderCell(getHeaderCell("Rating"));
        table.addHeaderCell(getHeaderCell("Time Stamp"));
        table.addHeaderCell(getHeaderCell("Comment"));

        tour.getLogs().forEach(log -> {
            table.addCell(String.valueOf(log.getId()));
            table.addCell(String.valueOf(log.getDuration()) + "h");
            table.addCell(String.valueOf(log.getDifficulty()));
            table.addCell(String.valueOf(log.getRating()));
            table.addCell(String.valueOf(log.getTimeStamp()));
            table.addCell(String.valueOf(log.getComment()));
        });
        return table;
    }

    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }

    private Paragraph generateSectionHeader(String header) throws IOException {
        return new Paragraph(header)
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold();
    }

    private Paragraph generateTourHeader(String header) throws IOException {
        return new Paragraph(header)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(26)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
    }

    private Paragraph generateSectionText(String content) throws IOException {
        return new Paragraph(content)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12);
    }




}
