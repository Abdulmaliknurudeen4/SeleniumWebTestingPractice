package org.example.excelDriven;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class dataDriven {

    public ArrayList<String> getData(String testCase) throws IOException {
        ArrayList<String> a = new ArrayList<>();

        String filename = "src/main/resources/Book1.xlsx";
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filename));
        Sheet testDataSheet = workbook.getSheet("Sheet1");

        Iterator<Row> rows = testDataSheet.iterator();
        Row firstRow = rows.next();

        Iterator<Cell> cellIterator = firstRow.cellIterator();
        int k = 0;
        int column=0;
        while (cellIterator.hasNext()){
            if(cellIterator.next().getStringCellValue().equalsIgnoreCase("Testcases")){
                column = k;
            }
            k++;
        }
        System.out.println(column);


        while(rows.hasNext()){
            Row r = rows.next();
            if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCase)){
                Iterator<Cell> cv = r.cellIterator();
                while(cv.hasNext()){
                    Cell c = cv.next();
                    if(c.getCellType() == CellType.STRING){
                        a.add(cv.next().getStringCellValue());
                    }else {

                        a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                    }
                }
            }
        }
        return a;
    }
}
