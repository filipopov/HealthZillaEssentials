package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.excel;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Review;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReviewExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<Review> reviewList;

    public ReviewExcelExporter(List<Review> reviewList) {
        this.reviewList=reviewList;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell=row.createCell(columnCount);
        if(value instanceof Long) {
            cell.setCellValue((Long) value);
        }
        else if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine() {
        sheet=workbook.createSheet("Review");

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row,0,"Review Information",style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        font.setFontHeightInPoints((short)(10));

        row=sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Review Id", style);
        createCell(row, 1, "User name", style);
        createCell(row, 2, "User username", style);
        createCell(row, 3, "Review Message", style);
        createCell(row, 4, "Type Review", style);
    }

    private void writeDataLines() {
        int rowCount=2;

        CellStyle style=workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(Review review: reviewList) {
            Row row=sheet.createRow(rowCount++);
            int columnCount=0;
            createCell(row, columnCount++, review.getId(), style);
            createCell(row, columnCount++, review.getUser().getName(), style);
            createCell(row, columnCount++, review.getUser().getUsername(), style);
            createCell(row, columnCount++, review.getMessage(), style);
            createCell(row, columnCount++, review.getClass().getSimpleName().equals("ComplaintReview") ? "Complaint" : "Praise", style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream=response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
