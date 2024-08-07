package com.ezio.OnlineExamPortal.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezio.OnlineExamPortal.entity.Questions;
import com.ezio.OnlineExamPortal.entity.Topic;

@Component
public class ExcelHelper {
	
	
	
	/************************
	 * Start code : For excel uploading with auto generating queId
	 * *************************** */

//	private static List<Questions> processSheet(Sheet sheet) {
//        List<Questions> sheetQuestions = new ArrayList<>();
//        Iterator<Row> iterator = sheet.iterator();
//        int rowNumber = 0;
//
//        while (iterator.hasNext()) {
//            Row row = iterator.next();
//
//            if (rowNumber == 0) {
//                rowNumber++;
//                continue;
//            }
//
//            // Process each row and add to sheetQuestions list
//            Questions q = new Questions();
//
//            Iterator<Cell> cells = row.iterator();
//            int cid = 0;
//
//            while (cells.hasNext()) {
//                Cell cell = cells.next();
//                switch (cid) {
//                    case 0:
//                        if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setQueId((long) cell.getNumericCellValue());
//                        } else if (cell.getCellType() == CellType.STRING) {
//                            q.setQueId(Long.parseLong(cell.getStringCellValue()));
//                        }
//                        break;
//                    case 1:
//                        if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setQueNum((int) cell.getNumericCellValue());
//                        } else if (cell.getCellType() == CellType.STRING) {
//                            q.setQueNum(Integer.parseInt(cell.getStringCellValue()));
//                        }
//                        break;
//                    case 2:
//                        if (cell.getCellType() == CellType.STRING) {
//                            q.setQuestions(cell.getStringCellValue());
//                        } else if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setQuestions(String.valueOf(cell.getNumericCellValue()));
//                        }
//                        break;
//                    case 3:
//                        if (cell.getCellType() == CellType.STRING) {
//                            q.setOption1(cell.getStringCellValue());
//                        } else if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setOption1(String.valueOf(cell.getNumericCellValue()));
//                        }
//                        break;
//                    case 4:
//                        if (cell.getCellType() == CellType.STRING) {
//                            q.setOption2(cell.getStringCellValue());
//                        } else if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setOption2(String.valueOf(cell.getNumericCellValue()));
//                        }
//                        break;
//                    case 5:
//                        if (cell.getCellType() == CellType.STRING) {
//                            q.setOption3(cell.getStringCellValue());
//                        } else if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setOption3(String.valueOf(cell.getNumericCellValue()));
//                        }
//                        break;
//                    case 6:
//                        if (cell.getCellType() == CellType.STRING) {
//                            q.setOption4(cell.getStringCellValue());
//                        } else if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setOption4(String.valueOf(cell.getNumericCellValue()));
//                        }
//                        break;
//                    case 7:
//                        if (cell.getCellType() == CellType.STRING) {
//                            q.setAnswer(cell.getStringCellValue());
//                        } else if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setAnswer(String.valueOf(cell.getNumericCellValue()));
//                        }
//                        break;
//                    case 8:
//                        if (cell.getCellType() == CellType.NUMERIC) {
//                            q.setMarks((int) cell.getNumericCellValue());
//                        } else if (cell.getCellType() == CellType.STRING) {
//                            q.setMarks(Integer.parseInt(cell.getStringCellValue()));
//                        }
//                        break;
//                    default:
//                        break;
//                }
//                cid++;
//            }
//            sheetQuestions.add(q);
//        }
//
//        return sheetQuestions;
//    }
//
//    public static List<Questions> parseExcelFile(MultipartFile file) throws IOException {
//        List<Questions> questionsList = new ArrayList<>();
//        try (InputStream is = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(is)) {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                // Skip the header row by checking the row number
//                if (row.getRowNum() == 0) {
//                    continue;
//                }
//
//                Questions questions = new Questions();
//
//                // Check for null cells and handle them
//                Cell idCell = row.getCell(0);
//                if (idCell != null && idCell.getCellType() == CellType.NUMERIC) {
//                    questions.setQueId((long) idCell.getNumericCellValue());
//                }
//
//                Cell numCell = row.getCell(1);
//                if (numCell != null && numCell.getCellType() == CellType.NUMERIC) {
//                    questions.setQueNum((int) numCell.getNumericCellValue());
//                }
//
//                Cell questionCell = row.getCell(2);
//                if (questionCell != null && questionCell.getCellType() == CellType.STRING) {
//                    questions.setQuestions(questionCell.getStringCellValue());
//                } else if (questionCell != null && questionCell.getCellType() == CellType.NUMERIC) {
//                    questions.setQuestions(String.valueOf(questionCell.getNumericCellValue()));
//                }
//
//                Cell option1Cell = row.getCell(3);
//                if (option1Cell != null && option1Cell.getCellType() == CellType.STRING) {
//                    questions.setOption1(option1Cell.getStringCellValue());
//                } else if (option1Cell != null && option1Cell.getCellType() == CellType.NUMERIC) {
//                    questions.setOption1(String.valueOf(option1Cell.getNumericCellValue()));
//                }
//
//                Cell option2Cell = row.getCell(4);
//                if (option2Cell != null && option2Cell.getCellType() == CellType.STRING) {
//                    questions.setOption2(option2Cell.getStringCellValue());
//                } else if (option2Cell != null && option2Cell.getCellType() == CellType.NUMERIC) {
//                    questions.setOption2(String.valueOf(option2Cell.getNumericCellValue()));
//                }
//
//                Cell option3Cell = row.getCell(5);
//                if (option3Cell != null && option3Cell.getCellType() == CellType.STRING) {
//                    questions.setOption3(option3Cell.getStringCellValue());
//                } else if (option3Cell != null && option3Cell.getCellType() == CellType.NUMERIC) {
//                    questions.setOption3(String.valueOf(option3Cell.getNumericCellValue()));
//                }
//
//                Cell option4Cell = row.getCell(6);
//                if (option4Cell != null && option4Cell.getCellType() == CellType.STRING) {
//                    questions.setOption4(option4Cell.getStringCellValue());
//                } else if (option4Cell != null && option4Cell.getCellType() == CellType.NUMERIC) {
//                    questions.setOption4(String.valueOf(option4Cell.getNumericCellValue()));
//                }
//
//                Cell answerCell = row.getCell(7);
//                if (answerCell != null && answerCell.getCellType() == CellType.STRING) {
//                    questions.setAnswer(answerCell.getStringCellValue());
//                } else if (answerCell != null && answerCell.getCellType() == CellType.NUMERIC) {
//                    questions.setAnswer(String.valueOf(answerCell.getNumericCellValue()));
//                }
//
//                Cell marksCell = row.getCell(8);
//                if (marksCell != null && marksCell.getCellType() == CellType.NUMERIC) {
//                    questions.setMarks((int) marksCell.getNumericCellValue());
//                } else if (marksCell != null && marksCell.getCellType() == CellType.STRING) {
//                    questions.setMarks(Integer.parseInt(marksCell.getStringCellValue()));
//                }
//
//                questionsList.add(questions);
//                System.out.println("Parsed Question: " + questions);
//            }
//        }
//        return questionsList;
//    }
//
//    public static boolean checkExcelFormat(MultipartFile file) {
//        String contentType = file.getContentType();
//        return contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//    }
//
//    public static List<Questions> convertExcelToListOfQuestions(Sheet sheet) {
//        return processSheet(sheet);
//    }
//	
	
	
	public static List<Questions> convertExcelToListOfQuestions(Sheet sheet) throws IOException {
	    List<Questions> sheetQuestions = new ArrayList<>();
	    Iterator<Row> iterator = sheet.iterator();
	    int rowNumber = 0;

	    // Map to store row number and image data
	    Map<Integer, byte[]> imageMap = extractImages(sheet);

	    while (iterator.hasNext()) {
	        Row row = iterator.next();

	        if (rowNumber == 0) {
	            rowNumber++;
	            continue;
	        }

	        Questions q = new Questions();
	        Iterator<Cell> cells = row.iterator();
	        int cid = 0;

	        while (cells.hasNext()) {
	            Cell cell = cells.next();
	            switch (cid) {
	                case 0:
	                    if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setQueId((long) cell.getNumericCellValue());
	                    } else if (cell.getCellType() == CellType.STRING) {
	                        q.setQueId(Long.parseLong(cell.getStringCellValue()));
	                    }
	                    break;
	                case 1:
	                    if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setQueNum((int) cell.getNumericCellValue());
	                    } else if (cell.getCellType() == CellType.STRING) {
	                        q.setQueNum(Integer.parseInt(cell.getStringCellValue()));
	                    }
	                    break;
	                case 2:
	                    if (cell.getCellType() == CellType.STRING) {
	                        q.setQuestions(cell.getStringCellValue());
	                    } else if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setQuestions(String.valueOf(cell.getNumericCellValue()));
	                    }
	                    break;
	                case 3:
	                    if (cell.getCellType() == CellType.STRING) {
	                        q.setOption1(cell.getStringCellValue());
	                    } else if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setOption1(String.valueOf(cell.getNumericCellValue()));
	                    }
	                    break;
	                case 4:
	                    if (cell.getCellType() == CellType.STRING) {
	                        q.setOption2(cell.getStringCellValue());
	                    } else if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setOption2(String.valueOf(cell.getNumericCellValue()));
	                    }
	                    break;
	                case 5:
	                    if (cell.getCellType() == CellType.STRING) {
	                        q.setOption3(cell.getStringCellValue());
	                    } else if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setOption3(String.valueOf(cell.getNumericCellValue()));
	                    }
	                    break;
	                case 6:
	                    if (cell.getCellType() == CellType.STRING) {
	                        q.setOption4(cell.getStringCellValue());
	                    } else if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setOption4(String.valueOf(cell.getNumericCellValue()));
	                    }
	                    break;
	                case 7:
	                    if (cell.getCellType() == CellType.STRING) {
	                        q.setAnswer(cell.getStringCellValue());
	                    } else if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setAnswer(String.valueOf(cell.getNumericCellValue()));
	                    }
	                    break;
	                case 8:
	                    if (cell.getCellType() == CellType.NUMERIC) {
	                        q.setMarks((int) cell.getNumericCellValue());
	                    } else if (cell.getCellType() == CellType.STRING) {
	                        q.setMarks(Integer.parseInt(cell.getStringCellValue()));
	                    }
	                    break;
	                default:
	                    break;
	            }
	            cid++;
	        }

	        // Associate image data with the current row
	        byte[] imageData = imageMap.get(row.getRowNum());
	        if (imageData != null) {
	            q.setImageData(imageData);
	        }

	        sheetQuestions.add(q);
	    }

	    return sheetQuestions;
	}


	private static Map<Integer, byte[]> extractImages(Sheet sheet) throws IOException {
	    Map<Integer, byte[]> imageMap = new HashMap<>();
	    if (sheet instanceof XSSFSheet) {
	        XSSFSheet xssfSheet = (XSSFSheet) sheet;
	        List<XSSFPictureData> pictures = xssfSheet.getWorkbook().getAllPictures();

	        for (POIXMLDocumentPart dr : xssfSheet.getRelations()) {
	            if (dr instanceof XSSFDrawing) {
	                XSSFDrawing drawing = (XSSFDrawing) dr;
	                for (XSSFShape shape : drawing.getShapes()) {
	                    if (shape instanceof XSSFPicture) {
	                        XSSFPicture picture = (XSSFPicture) shape;
	                        XSSFPictureData pictureData = picture.getPictureData();
	                        XSSFClientAnchor anchor = picture.getPreferredSize();
	                        int rowIndex = anchor.getRow1();

	                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	                        byteArrayOutputStream.write(pictureData.getData());
	                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

	                        imageMap.put(rowIndex, imageBytes);
	                    }
	                }
	            }
	        }
	    }
	    return imageMap;
	}


    public static List<Questions> parseExcelFile(MultipartFile file) throws IOException {
        List<Questions> questionsList = new ArrayList<>();
        try (InputStream is = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            questionsList = convertExcelToListOfQuestions(workbook.getSheetAt(0));
        }
        return questionsList;
    }

    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
	/************************
	 * End of the code For excel uploading with auto generating queId
	 * *************************** */

    


//	private static List<Questions> processSheet(Sheet sheet) {
//	    List<Questions> sheetQuestions = new ArrayList<>();
//	    
//	    Iterator<Row> iterator = sheet.iterator();
//	    int rowNumber = 0;
//	    
//	    while (iterator.hasNext()) {
//	        Row row = iterator.next();
//	        
//	        if (rowNumber == 0) {
//	            rowNumber++;
//	            continue;
//	        }
//	        
//	        // Process each row and add to sheetQuestions list
//	        Questions q = new Questions();
//	        
//	        Iterator<Cell> cells = row.iterator();
//	        int cid = 0;
//	        
//	        while (cells.hasNext()) {
//	            Cell cell = cells.next();
//	            switch (cid) {
////	                case 0:
////	                    if (cell.getCellType() == CellType.NUMERIC) {
////	                        q.setQueId((long) cell.getNumericCellValue());
////	                    } else {
////	                        // Handle non-numeric value
////	                    	 q.setQuestions("");
////	                    }
////	                    break;
//	                case 0:
//	                    q.setQuestions(cell.getStringCellValue());
//	                    break;
//	                case 1:
//	                    q.setOption1(cell.getStringCellValue());
//	                    break;
//	                case 2:
//	                    q.setOption2(cell.getStringCellValue());
//	                    break;
//	                case 3:
//	                    q.setOption3(cell.getStringCellValue());
//	                    break;
//	                case 4:
//	                    q.setOption4(cell.getStringCellValue());
//	                    break;
//	                case 5:
//	                    q.setAnswer(cell.getStringCellValue());
//	                    break;
//	                default:
//	                    break;
//	            }
//	            cid++;
//	        }
//	        sheetQuestions.add(q);
//	    }
//	    
//	    return sheetQuestions;
//	}

//	public static boolean checkExcelFormat(MultipartFile file) {
//		String contentType = file.getContentType();
//		return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//	}

	
}