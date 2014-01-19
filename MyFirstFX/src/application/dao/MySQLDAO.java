package application.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.dto.Question;
import application.dto.QuestionsList;
import application.dto.Test;
import application.dto.TestsList;

public class MySQLDAO {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  
  protected static final String selectSQLAllQuestions = "SELECT * FROM question";
  protected static final String selectSQLQuestion = "SELECT * FROM question WHERE ID = ?";
  protected static final String insertSQLQuestion = "INSERT INTO Question (Body, Answer) VALUES(?, ?)";
  protected static final String updateSQLQuestion = "UPDATE Question SET  Body = ?, Answer = ? WHERE ID = ?";
  protected static final String deleteSQLQuestion = "DELETE FROM Question WHERE ID = ?";
  
  protected static final String selectSQLAllTestsOnly = "SELECT * FROM test";
  protected static final String selectSQLTestOnly = "SELECT * FROM test WHERE ID = ?";
  protected static final String selectSQLTestQuestions = "SELECT q.*  FROM Test t INNER JOIN test_questions tq ON t.ID = tq.TID"
		  +" INNER JOIN Question q ON tq.QID = q.ID WHERE t.ID = ?";
  protected static final String insertSQLTest = "INSERT INTO Test (Name) VALUES(?)";
  protected static final String updateSQLTest = "UPDATE Test SET Name = ? WHERE ID = ?";
  protected static final String deleteSQLTest = "DELETE FROM Test WHERE ID = ?";
  
  protected static final String insertSQLTestQuestion = "INSERT INTO test_questions (Hash, QID, TID) VALUES(?, ?, ?)";
  protected static final String deleteSQLAllTestQuestions = "DELETE FROM test_questions WHERE TID = ?";
  protected static final String deleteSQLQuestionFromAllTests = "DELETE FROM test_questions WHERE QID = ?";
  protected static final String deleteSQLTestQuestion = "DELETE FROM test_question WHERE Hash = ? AND TID = ? AND QID = ?";
  
  
  public MySQLDAO() throws Exception {
	  
      try {
    	// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		
		// Setup the connection with the DB
	      connect = DriverManager
	          .getConnection("jdbc:mysql://localhost/withmatching?"
	              + "user=admin&password=localhostadmin&useUnicode=true");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		throw e;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		throw e;
	}
      
  }
  
  // ========== INSERT Methods ==================
  
  public Question insertQuestion (Question q) throws Exception {
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(insertSQLQuestion);
		  int i = 1;
		  //build prepared statement
		  preparedStatement.setString(i++, q.getBody());
		  preparedStatement.setString(i++, q.getAnswer());
		  
		  preparedStatement.executeUpdate();
		  
		  resultSet = preparedStatement.getGeneratedKeys();
		  
		  while (resultSet.next()) {
			  q.setId(resultSet.getInt("ID"));
		  }
		  
	      //preparedStatement
	      return q;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
	  
  }
  
  public Test insertTest(Test t) throws Exception {
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(insertSQLTest);
		  int i = 1;
		  //build prepared statement
		  preparedStatement.setString(i++, t.getName());
		  
		  
		  preparedStatement.executeUpdate();
		  
		  resultSet = preparedStatement.getGeneratedKeys();
		  
		  while (resultSet.next()) {
			  t.setId(resultSet.getInt("ID"));
		  }
		  
		  //save test questions
		  saveTestQuestions(t);
		  
	      //preparedStatement
	      return t;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
	  
  }
  
//========== UPDATE Methods ==================
  
  public Question updateQuestion (Question q) throws Exception {
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(updateSQLQuestion);
		  int i = 1;
		  //build prepared statement
		  preparedStatement.setString(i++, q.getBody());
		  preparedStatement.setString(i++, q.getAnswer());
		  preparedStatement.setInt(i++, q.getId());
		  
		  preparedStatement.executeUpdate();
		  
		  
	      
	      return q;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
	  
  }
  
  public Test updateTest(Test t) throws Exception {
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(updateSQLTest);
		  int i = 1;
		  //build prepared statement
		  preparedStatement.setString(i++, t.getName());
		  preparedStatement.setInt(i++, t.getId());
		  
		  
		  preparedStatement.executeUpdate();
		  
		//save test questions
		  saveTestQuestions(t);
	      
	      return t;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
	  
  }
  
//========== DELETE Methods ==================
  
  public Question deleteQuestion (Question q) throws Exception {
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(deleteSQLQuestion);
		 
		  //build prepared statement
		  
		  preparedStatement.setInt(1, q.getId());
		  
		  preparedStatement.executeUpdate();
		  
		  
	      
	      return q;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
	  
  }
  
  public Test deleteTest(Test t) throws Exception {
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(deleteSQLTest);
		  //build prepared statement
		  preparedStatement.setInt(1, t.getId());
		  
		  
		  preparedStatement.executeUpdate();
		  
		  
	      
	      return t;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
	  
  }
  
  /**
   * Save the questions related to a particular <code>Test</code>.
   * 
   * @param t
   * @return <code>Test</code>
   * @throws Exception
   */
  public Test saveTestQuestions(Test t) throws Exception {
	try {
			  
		//delete all test questions
		deleteAllTestQuestions(t);
		
		//insert modified questions list
		for (Question q: t.getQuestions()) {
		  //prepare statement
		  preparedStatement = connect.prepareStatement(insertSQLTestQuestion);
		  //build prepared statement
		  int i = 1;
		  
		  preparedStatement.setString(i++, Question.generateHash(q.getId(), t.getId()));
		  preparedStatement.setInt(i++, q.getId());
		  preparedStatement.setInt(i++, t.getId());
		  
		  
		  preparedStatement.executeUpdate();
		  
		}
	      
	      return t;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
		}
  }
  
  /**
   * Delete the questions of a particular <code>Test</code>. The method removes only the relations not the question themself.
   * 
   * @param t
   * @return <code>Test</code>
   * @throws Exception
   */
  public Test deleteAllTestQuestions(Test t) throws Exception {
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(deleteSQLAllTestQuestions);
		  //build prepared statement
		  preparedStatement.setInt(1, t.getId());
		  
		  
		  preparedStatement.executeUpdate();
		  
		  
	      
	      return t;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
  }
  
  /**
   * Load all questions included in a particular <code>Test</code>
   * 
   * @param tid
   * @return <code>QuestionsList</code>
   * @throws Exception
   */
  public QuestionsList loadQuestionsByTest(int tid) throws Exception {
	  QuestionsList qList = new QuestionsList();
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(selectSQLTestQuestions);
		  //build prepared statement
		  preparedStatement.setInt(1, tid);
		  
		  
		  resultSet = preparedStatement.executeQuery();
		  
		  while(resultSet.next()) {
			  Question q = new Question();
			  build(q, resultSet);
			  
			  
			  qList.add(q);
		  }
		  
		  
	      
	      return qList;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
  }
  
  /**
   * Load absolutely all questions.
   * 
   * @return <code>QuestionsList</code>
   * @throws Exception
   */
  public QuestionsList loadAllQuestions() throws Exception {
	  QuestionsList qList = new QuestionsList();
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(selectSQLAllQuestions);
		  
		  resultSet = preparedStatement.executeQuery();
		  
		  while(resultSet.next()) {
			  Question q = new Question();
			  build(q, resultSet);
			  
			  
			  qList.add(q);
		  }
		  
		  
	      
	      return qList;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
  }
  
  /**
   * Load a single <code>Question</code>.
   * 
   * @param qid
   * @return <code>Question</code>
   * @throws Exception
   */
  public Question loadQuestion(int qid) throws Exception {
	  
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(selectSQLQuestion);
		  
		  preparedStatement.setInt(1, qid);
		  
		  resultSet = preparedStatement.executeQuery();
		  
		  Question q = new Question();
		  
		  while(resultSet.next()) {
			  
			  build(q, resultSet);
			  
		  }
		  
		  
	      
	      return q;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
  }
  
  /**
   * Load a single <code>Test</code> with its <code>QuestionsList</code>.
   * 
   * @param tid
   * @return <code>Test</code>
   * @throws Exception
   */
  public Test loadTest(int tid) throws Exception {
	  
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(selectSQLTestOnly);
		  
		  preparedStatement.setInt(1, tid);
		  
		  resultSet = preparedStatement.executeQuery();
		  
		  Test t = new Test();
		  
		  while(resultSet.next()) {
			  
			  build(t, resultSet);
			  
		  }
		  
		  t.setQuestions(loadQuestionsByTest(t.getId()));
	      
	      return t;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
  }
  
  public TestsList loadAllTests() throws Exception {
	  TestsList tList = new TestsList();
	  try {
		  
		  //prepare statement
		  preparedStatement = connect.prepareStatement(selectSQLAllTestsOnly);
		  
		  resultSet = preparedStatement.executeQuery();
		  
		  
		  
		  while(resultSet.next()) {
			  Test t = new Test();
			  build(t, resultSet);
			  
			  tList.add(t);
			  
		  }
		  
	      
	      return tList;
	    } catch (SQLException e) {
		  throw new Exception("SQL Error: "+e.getMessage());
		} finally {
	      close();
	    }
  }
  
  
  
  
  
  // BUILD METHODS
  
  protected void build(Question q, ResultSet rs) throws SQLException {
	  q.setId(rs.getInt("ID"));
	  q.setAnswer(rs.getString("Answer"));
	  q.setBody(rs.getString("Body"));
  }
  
  public void build(Test t, ResultSet rs) throws SQLException {
	  t.setId(rs.getInt("ID"));
	  t.setName(rs.getString("Name"));
  }
  
  /**
   * Prints out the first question. Use this method to test the DB connectivity.
   * It assumes that you have at least one question in the Question table.
   * 
   * 
   * @throws Exception
   */
  public void testDataBase() throws Exception {
    try {
     

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from Question LIMIT 1");
      //writeResultSet(resultSet);

      while (resultSet.next()) {
          // It is possible to get the columns via name
          // also possible to get the columns via the column number
          // which starts at 1
          // e.g. resultSet.getSTring(2);
          
          System.out.println("ID: " + resultSet.getInt("ID"));
          System.out.println("Body: " + resultSet.getString("Body"));
          System.out.println("Answer: " + resultSet.getString("Answer"));

        }
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }
  
//You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
      
      if (preparedStatement != null) {
    	  preparedStatement.close();
      }
    } catch (Exception e) {

    }
  }
}
