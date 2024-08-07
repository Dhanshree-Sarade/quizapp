package com.ezio.OnlineExamPortal.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ezio.OnlineExamPortal.entity.Questions;
import com.ezio.OnlineExamPortal.entity.Topic;
import com.ezio.OnlineExamPortal.helper.ExcelHelper;
import com.ezio.OnlineExamPortal.repository.QuestionsRepository;
import com.ezio.OnlineExamPortal.repository.TopicRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionsService {

    @Autowired
    private QuestionsRepository questionsRepository;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private ExcelHelper excelHelper;
    
    
//    // this is updated correct code for only uploading excel file 
//    public void saveQuestionsFromExcel(MultipartFile file) {
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0); 
//            List<Questions> questionsList = ExcelHelper.convertExcelToListOfQuestions(sheet);
//
//            // Save the Questions entities
//            questionsRepository.saveAll(questionsList);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
//        }
//    }

    
   
    
    
    
    
    /************************
	 * Start code : For excel uploading with auto generating queId
	 * *************************** */
    
    
//    public void saveQuestionsFromExcel(MultipartFile file, Long topicId) throws Exception {
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//        	
//        	//System.out.println("88888888888888");
//        	
//            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet
//            List<Questions> questionsList = ExcelHelper.convertExcelToListOfQuestions(sheet);
//
//            System.out.println("sfjfnjrgbh");
//            
//            // Fetch or create the Topic entity by its ID
//            Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
//
//            // Set the topic for each Questions entity
//            for (Questions question : questionsList) {
//                question.setTopic(topic);
//            }
//
//            // Save the Questions entities
//            questionsRepository.saveAll(questionsList);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
//        }
//        
//        
//     // Assuming the upload is successful, update the status field for the topic
//        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
//        if (optionalTopic.isPresent()) {
//        	System.out.println("hghcb");
//            Topic topic = optionalTopic.get();
//            topic.setStatus(true); // Set status to true indicating the file is uploaded
//            System.out.println(topic.isStatus());
//            topicRepository.save(topic); // Save the updated topic entity
//        } else {
//            // Handle the case where the topic with the specified ID is not found
//            throw new Exception("Topic with ID " + topicId + " not found");
//        }
//    }
    
    public void saveQuestionsFromExcel(MultipartFile file, Long topicId) throws Exception {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet
            List<Questions> questionsList = ExcelHelper.convertExcelToListOfQuestions(sheet);

            // Fetch or create the Topic entity by its ID
            Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

            // Set the topic for each Questions entity
            for (Questions question : questionsList) {
                question.setTopic(topic);
            }

            // Save the Questions entities
            questionsRepository.saveAll(questionsList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        // Assuming the upload is successful, update the status field for the topic
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setStatus(true); // Set status to true indicating the file is uploaded
            topicRepository.save(topic); // Save the updated topic entity
        } else {
            // Handle the case where the topic with the specified ID is not found
            throw new Exception("Topic with ID " + topicId + " not found");
        }
    }
    
    
    
    public List<Questions> getQuestions(Long topicId) {
    	Topic topic = topicRepository.findById(topicId).orElse(null);
    	 return topic.getQuestions().stream().map(question -> {
    		 Questions questions = new Questions();
    		 questions.setQueId(question.getQueId());
    		 questions.setQueNum(question.getQueNum());
    		 questions.setQuestions(question.getQuestions());
    		 questions.setOption1(question.getOption1());
    		 questions.setOption2(question.getOption2());
    		 questions.setOption3(question.getOption3());
    		 questions.setOption4(question.getOption4());
    		 questions.setAnswer(question.getAnswer());
    		 questions.setMarks(question.getMarks());
    		 questions.setImageData(question.getImageData());
             return questions;
         }).collect(Collectors.toList());
    	 
    }
    
    
    public void updateTopicWithExcel(Long topicId, MultipartFile file) throws Exception {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet
            List<Questions> newQuestionsList = ExcelHelper.convertExcelToListOfQuestions(sheet);

            // Fetch the Topic entity by its ID
            Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));

            // Fetch existing questions associated with the topic
            List<Questions> existingQuestions = questionsRepository.findByTopic(topic);

            // Log existing questions
            System.out.println("Existing Questions: " + existingQuestions);

            // Map to hold existing questions by their ID for easy lookup
            Map<Long, Questions> existingQuestionsMap = existingQuestions.stream()
                    .collect(Collectors.toMap(Questions::getQueId, q -> q));

            // Iterate over new questions and update or add them
            for (Questions newQuestion : newQuestionsList) {
                if (newQuestion.getQueId() != null && existingQuestionsMap.containsKey(newQuestion.getQueId())) {
                    // Update existing question
                    Questions existingQuestion = existingQuestionsMap.get(newQuestion.getQueId());
                    existingQuestion.setQueNum(newQuestion.getQueNum());
                    existingQuestion.setQuestions(newQuestion.getQuestions());
                    existingQuestion.setOption1(newQuestion.getOption1());
                    existingQuestion.setOption2(newQuestion.getOption2());
                    existingQuestion.setOption3(newQuestion.getOption3());
                    existingQuestion.setOption4(newQuestion.getOption4());
                    existingQuestion.setAnswer(newQuestion.getAnswer());
                    existingQuestion.setMarks(newQuestion.getMarks());
                    existingQuestion.setImageData(newQuestion.getImageData()); // Update image data
                    System.out.println("Updated Question: " + existingQuestion);
                } else {
                    // Add new question
                    newQuestion.setTopic(topic);
                    existingQuestions.add(newQuestion);
                    System.out.println("Added New Question: " + newQuestion);
                }
            }

            // Save the updated questions
            questionsRepository.saveAll(existingQuestions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating questions: " + e.getMessage());
        }
    }


    private static List<Questions> parseExcelFile(MultipartFile file) throws IOException {
        List<Questions> questionsList = new ArrayList<>();
        try (InputStream is = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            int rowNumber = 0;

            while (iterator.hasNext()) {
                Row row = iterator.next();

                // Skip the header row by checking the row number
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
                questionsList.add(q);
                System.out.println("Parsed Question: " + q);
            }
        }
        return questionsList;
    }



	/************************
	 * End of the code For excel uploading with auto generating queId
	 * *************************** */

    
    
    
    
    
//    public Topic updateTopicWithExcel(Long topicId, MultipartFile file) throws Exception {
//        Topic existingTopic = topicRepository.findById(topicId).orElse(null);
//        if (existingTopic == null) {
//            throw new Exception("Topic not found with id: " + topicId);
//        }
//
//        List<Questions> updatedQuestions = ExcelHelper.parseExcelFile(file);
//
//        for (Questions newQuestion : updatedQuestions) {
//            Long queId = newQuestion.getQueId();
//            if (queId == null) {
//                System.out.println("Question ID is null for question: " + newQuestion.getQuestions());
//                continue; // Skip questions with null IDs
//            }
//
//            Optional<Questions> existingQuestionOpt = questionsRepository.findById(queId);
//            if (existingQuestionOpt.isPresent()) {
//                Questions existingQuestion = existingQuestionOpt.get();
//                existingQuestion.setQuestions(newQuestion.getQuestions());
//                existingQuestion.setOption1(newQuestion.getOption1());
//                existingQuestion.setOption2(newQuestion.getOption2());
//                existingQuestion.setOption3(newQuestion.getOption3());
//                existingQuestion.setOption4(newQuestion.getOption4());
//                existingQuestion.setAnswer(newQuestion.getAnswer());
//                questionsRepository.save(existingQuestion);
//            } else {
//                System.out.println("No existing question found with ID: " + queId);
//                newQuestion.setTopic(existingTopic);
//                questionsRepository.save(newQuestion);
//            }
//        }
//
//        return topicRepository.save(existingTopic);
//    }
    
    
    
    //this is update excel method 
    
//    public void updateTopicWithExcel(Long topicId, MultipartFile file) throws IOException {
//        // Retrieve existing topic and questions
//        Topic topic = topicRepository.findById(topicId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid topic ID"));
//        System.out.println("topic id is : " + topicId);
//        List<Questions> existingQuestions = topic.getQuestions();
//
//        // Parse the new questions from the Excel file
//        List<Questions> newQuestions = ExcelHelper.parseExcelFile(file);
//
//        // Update existing questions and add new ones
//        for (Questions newQuestion : newQuestions) {
//            Long newQuestionId = newQuestion.getQueId();
//            if (newQuestionId != null) {
//                // Check if the question with the same id exists
//                Optional<Questions> existingQuestionOptional = existingQuestions.stream()
//                        .filter(question -> question.getQueId().equals(newQuestionId))
//                        .findFirst();
//                if (existingQuestionOptional.isPresent()) {
//                    // Update existing question if any changes
//                    Questions existingQuestion = existingQuestionOptional.get();
//                    if (!existingQuestion.equals(newQuestion)) {
//                        existingQuestion.setQuestions(newQuestion.getQuestions());
//                        existingQuestion.setOption1(newQuestion.getOption1());
//                        existingQuestion.setOption2(newQuestion.getOption2());
//                        existingQuestion.setOption3(newQuestion.getOption3());
//                        existingQuestion.setOption4(newQuestion.getOption4());
//                        existingQuestion.setAnswer(newQuestion.getAnswer());
//                    }
//                } else {
//                    // Add new question if the question with the same id does not exist
//                    newQuestion.setTopic(topic);
//                    existingQuestions.add(newQuestion);
//                }
//            }
//        }
//
//        // Save updated topic
//        topicRepository.save(topic);
//    }


    
    
    
    
//    public Topic updateTopicWithExcel(Long topicId, MultipartFile file) throws Exception{
//    	Topic existingTopic = topicRepository.findById(topicId).orElseThrow();
//    	
//    	if (existingTopic == null) {
//    		System.out.println("existingTopic"+existingTopic);
//            throw new Exception("Topic not found with id: " + topicId);
//        }
//
//    	System.out.println("parseExcel");
//        // Process the Excel file and update questions
//        List<Questions> updatedQuestions = ExcelHelper.parseExcelFile(file);
//        //existingTopic.setQuestions(updatedQuestions);
//        System.out.println("check:-"+existingTopic);
//
//        for (Questions newQuestion : updatedQuestions) {
//            Optional<Questions> existingQuestionOpt = questionsRepository.findById(newQuestion.getQueId());
//            System.out.println("existingQuestionOpt:-"+existingQuestionOpt);
//            if (existingQuestionOpt.isPresent()) {
//                Questions existingQuestion = existingQuestionOpt.get();
//                existingQuestion.setQuestions(newQuestion.getQuestions());
//                existingQuestion.setOption1(newQuestion.getOption1());
//                existingQuestion.setOption2(newQuestion.getOption2());
//                existingQuestion.setOption3(newQuestion.getOption3());
//                existingQuestion.setOption4(newQuestion.getOption4());
//                existingQuestion.setAnswer(newQuestion.getAnswer());
//                System.out.println("existingQuestion:-"+existingQuestion);
//                questionsRepository.save(existingQuestion);
//            } else {
//                newQuestion.setTopic(existingTopic);
//                questionsRepository.save(newQuestion);
//            }
//        }
//        
//        return topicRepository.save(existingTopic);
//    }
//    
    
//	// this is correct code
//    public void saveQuestionsFromExcel(MultipartFile file) {
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//            List<Questions> questionsList = excelHelper.convertExcelToListOfQuestions(workbook.getSheetAt(0));
//            questionsRepository.saveAll(questionsList);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
//        }
//    }
	 

    
	

 // Ensure that the topic exists before saving questions
//    public void saveQuestionsFromExcel(MultipartFile file, Long topicId) {
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet
//            List<Questions> questionsList = ExcelHelper.convertExcelToListOfQuestions(sheet);
//            
//            // Fetch the Topic entity by its ID
//            Topic topic = topicService.getTopicById(topicId);
//            if (topic == null) {
//                // Create the topic if it does not exist
//                topic = new Topic();
//                // Populate topic properties here (e.g., name, description)
//                // Then save the topic
//                System.out.println("yuiu");
//                topic = topicRepository.save(topic);
//            }
//            
//            // Set the topic for each Questions entity
//            if (topic != null) {
//                for (Questions question : questionsList) {
//                    question.setTopic(topic);
//                    System.out.println("ssss");
//                }
//            }
//            
//            // Save the Questions entities
//            questionsRepository.saveAll(questionsList);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
//        }
//    }


//    public void saveQuestionsFromExcel(MultipartFile file, Long topicId) {
//        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet
//            List<Questions> questionsList = ExcelHelper.convertExcelToListOfQuestions(sheet);
//            
//            // Fetch the Topic entity by its ID
//            Topic topic = topicService.getTopicById(topicId);
//            if (topic == null) {
//                // Create the topic if it does not exist
//                topic = new Topic();
//                // Populate topic properties here (e.g., name, description)
//                // Then save the topic
//                topic = topicRepository.save(topic);
//            }
//            
//            // Set the topic for each Questions entity
//            if (topic != null) {
//                for (Questions question : questionsList) {
//                    question.setTopic(topic);
//                }
//            }
//            
//            // Save the Questions entities
//            questionsRepository.saveAll(questionsList);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
//        }
//    }



    
    public List<Questions> getAllQuestions() {
        return questionsRepository.findAll();
    }
    
    
    public void deleteQuestionsByTopicId(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        List<Questions> questions = questionsRepository.findByTopic(topic);

        questionsRepository.deleteAll(questions);
    }
}