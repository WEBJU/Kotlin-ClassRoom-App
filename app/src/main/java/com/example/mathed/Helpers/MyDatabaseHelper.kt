package com.example.mathed.Helpers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.mathed.data.TAnswer
import com.example.mathed.data.TStudent
import com.example.mathed.data.TTest
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

class MyDatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    fun isDatabaseImported(context: Context): Boolean {
        val db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).path, null, SQLiteDatabase.OPEN_READONLY)
        val tableExistsQuery = "SELECT name FROM sqlite_master WHERE type='table' AND name='TParent'"
        val tableExistsCursor = db.rawQuery(tableExistsQuery, null)
        val tableExists = tableExistsCursor.moveToFirst()
        tableExistsCursor.close()
        db.close()
        return tableExists
    }
    companion object {
        private const val DATABASE_NAME = "AssignmentDB.db"
        private const val DATABASE_VERSION = 1

    }
    fun importDatabase(context: Context) {
        val assetManager = context.assets
        val input = assetManager.open(DATABASE_NAME)
        val output = FileOutputStream(context.getDatabasePath(DATABASE_NAME))

        val buffer = ByteArray(1024)
        var length: Int = input.read(buffer)
        while (length > 0) {
            output.write(buffer, 0, length)
            length = input.read(buffer)
        }

        output.flush()
        output.close()
        input.close()
    }


    override fun onCreate(db: SQLiteDatabase) {

        // Check if the database file exists on the device
        val databaseFile = context.getDatabasePath(DATABASE_NAME)
        if (databaseFile.exists()) {
            // Database file already exists, do nothing
            return
        }

        // If the database file does not exist, create it and import the initial data
        importDatabase(context)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This method will be called if the database version is updated
        // You can use it to modify tables or do other tasks
    }


    @SuppressLint("Range")
    fun getQuestionsAndAnswers(): List<Pair<String, List<TAnswer>>> {
        val questionsAndAnswers = mutableListOf<Pair<String, List<TAnswer>>>()
        val db = readableDatabase
        val questionIds = mutableListOf<Int>()

        // Get 10 random question IDs from the Tquestions table
        val questionCursor = db.rawQuery("SELECT * FROM TQuestion ORDER BY RANDOM() LIMIT 10", null)

            while (questionCursor.moveToNext()) {
                val questionId = questionCursor.getInt(questionCursor.getColumnIndex("Id"))
                questionIds.add(questionId)
                val questionText = questionCursor.getString(questionCursor.getColumnIndex("QuestionText"))
                val answerCursor = db.rawQuery("SELECT * FROM TAnswer WHERE QuestionId = ?", arrayOf(questionId.toString()))
                val answers = mutableListOf<TAnswer>()
                while (answerCursor.moveToNext()) {
                    val id = answerCursor.getInt(answerCursor.getColumnIndexOrThrow("Id"))
                    val answerText = answerCursor.getString(answerCursor.getColumnIndexOrThrow("AnswerText"))
                    val isCorrect = answerCursor.getInt(answerCursor.getColumnIndexOrThrow("IsCorrect")) == 1
                    answers.add(TAnswer(id, questionId, answerText, isCorrect))
                }
                answerCursor.close()
                questionsAndAnswers.add(questionText to answers)
            }


        questionCursor.close()

        db.close()
        return questionsAndAnswers
    }

    fun insertData(name: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
        }
        db.insert("mytable", null, contentValues)
        db.close()
    }

    fun getAnswersForQuestion(questionId: Int): List<TAnswer> {
        val db = readableDatabase
        val query = "SELECT Id, QuestionId, AnswerText, IsCorrect FROM TAnswer WHERE QuestionId = ?"
        val selectionArgs = arrayOf(questionId.toString())
        val cursor = db.rawQuery(query, selectionArgs)
        val answers = mutableListOf<TAnswer>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"))
                val answerText = cursor.getString(cursor.getColumnIndexOrThrow("AnswerText"))
                val isCorrect = cursor.getInt(cursor.getColumnIndexOrThrow("IsCorrect")) == 1
                answers.add(TAnswer(id, questionId, answerText, isCorrect))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return answers
    }

    fun createUser(username: String, password: String): Boolean {
        val status = isDatabaseImported(context)

        if(!status){
            importDatabase(context)
        }

        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("UserName", username)
            put("UserPassWord", password)
        }
        val result = db.insert("TParent", null, contentValues)
        db.close()
        return result != -1L // return true if the insert was successful
    }
    fun createStudent(username: String, password: String): Boolean {
        val status = isDatabaseImported(context)

        if(!status){
            importDatabase(context)
        }
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("UserName", username)
            put("UserPassWord", password)
        }
        val result = db.insert("TStudent", null, contentValues)
        db.close()
        return result != -1L // return true if the insert was successful
    }

    fun authenticateUser(username: String, password: String): Boolean {
        val status = isDatabaseImported(context)

        if(!status){
            importDatabase(context)
        }
        val db = readableDatabase
        val query = "SELECT COUNT(*) FROM TParent WHERE UserName = ? AND UserPassWord = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.rawQuery(query, selectionArgs)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.moveToFirst()
        cursor.close()
        db.close()
        return count > 0 // return true if the user exists and the password matches
    }
    fun authenticateStudent(username: String, password: String): Boolean {
        val status = isDatabaseImported(context)

        if(!status){
            importDatabase(context)
        }
        val db = readableDatabase
        val query = "SELECT COUNT(*) FROM TStudent WHERE UserName = ? AND UserPassWord = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.rawQuery(query, selectionArgs)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.moveToFirst()
        cursor.close()
        db.close()
        return count > 0 // return true if the user exists and the password matches
    }

    fun checkUserExists(username: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM TParent WHERE UserName = ?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }
    fun checkStudentExists(username: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM TStudent WHERE UserName = ?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun getAllStudents(): ArrayList<TStudent> {
        val students = ArrayList<TStudent>()
        Log.d("Tag","before quewrt")
        val selectQuery = "SELECT * FROM TStudent ORDER BY Id DESC"
        val db = readableDatabase
        val cursor =  try {
            db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            Log.e("Tag", "Error executing query: ${e.message}")
            db.close()
            return students
        }
        if (cursor.count == 0) {

            Log.d("Tag","students db empty")
            cursor.close()

            db.close()
            return students
        }
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("UserName")) ?: ""
            val score = cursor.getString(cursor.getColumnIndexOrThrow("UserPassWord")) ?: ""
            val student = TStudent(id, name, score)
            students.add(student)
        }
        cursor.close()
        db.close()
        return students
    }
    fun getAllStudentsScores(): ArrayList<TTest> {
        val studentsScores = ArrayList<TTest>()

        val selectQuery = "SELECT * FROM TTest ORDER BY Id DESC"
        val db = readableDatabase
        val cursor =  try {
            db.rawQuery(selectQuery, null)
        } catch (e: Exception) {

            db.close()
            return studentsScores
        }
        if (cursor.count == 0) {


            cursor.close()

            db.close()
            return studentsScores
        }
        while (cursor.moveToNext()) {
            val id = cursor.getColumnIndexOrThrow("Id")
            val studentId = cursor.getColumnIndexOrThrow("StudentId")
            val score = cursor.getColumnIndexOrThrow("Score")
            val dateMillis = cursor.getLong(cursor.getColumnIndexOrThrow("Date"))
            val date = Date(dateMillis)
            val student = TTest(id, studentId, score,date)
            studentsScores.add(student)
        }
        cursor.close()
        db.close()
        return studentsScores
    }

    fun getMyScores(): ArrayList<TTest> {
        val myScores = ArrayList<TTest>()
        val userId = getUserIdFromUsername()

        val selectQuery = "SELECT Id, StudentId, Score, Date FROM TTest WHERE StudentId == $userId ORDER BY Id DESC"
        val db = readableDatabase
        val cursor = try {
            db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            db.close()
            return myScores
        }
        if (cursor.count == 0) {
            cursor.close()
            db.close()
            return myScores
        }
        while (cursor.moveToNext()) {
            val id = cursor.getColumnIndexOrThrow("Id")
            val studentId = cursor.getColumnIndexOrThrow("StudentId")
            val score = cursor.getColumnIndexOrThrow("Score")
            val dateMillis = cursor.getLong(cursor.getColumnIndexOrThrow("Date"))
            val date = Date(dateMillis)
            val student = TTest(id, studentId, score,date)
            Log.d("StudentScore",student.toString())
            myScores.add(student)
        }
        Log.d("myscores" ,myScores.toString())
        cursor.close()
        db.close()
        return myScores
    }

    fun getUserIdFromUsername(): Int {
        val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username=sharedPrefs.getString("username", null)
        val db = readableDatabase
        val selectQuery = "SELECT Id FROM TStudent WHERE username = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(username))
        val userId = if (cursor.moveToFirst()) cursor.getInt(0) else -1
        cursor.close()
        db.close()
        return userId
    }
    fun insertTTest(score: Int): Boolean {
        val userId = getUserIdFromUsername()
        val db = writableDatabase
        val values = ContentValues().apply {
            put("StudentId", userId)
            put("Score", score)
            put("Date", System.currentTimeMillis())
        }
        Log.d("Scores",values.toString())
        val result = db.insert("TTest", null, values)
        db.close()
        return result != -1L
    }
    fun getScoresGroupedByStudentId(): Map<Int, List<Triple<String, Int,Long>>> {
        val db = readableDatabase
        val selectQuery = "SELECT TTest.StudentId,TTest.Date, TStudent.UserName, TTest.Score " +
                "FROM TTest JOIN TStudent ON TTest.StudentId = TStudent.Id " +
                "ORDER BY TTest.StudentId ASC"
        val cursor = db.rawQuery(selectQuery, null)
        val scores = mutableMapOf<Int, MutableList<Triple<String, Int,Long>>>()
        while (cursor.moveToNext()) {
            val studentId = cursor.getInt(cursor.getColumnIndexOrThrow("StudentId"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("UserName"))
            val score = cursor.getInt(cursor.getColumnIndexOrThrow("Score"))
            val date = cursor.getLong(cursor.getColumnIndexOrThrow("Date"))
            if (!scores.containsKey(studentId)) {
                scores[studentId] = mutableListOf(Triple(name, score,date))
            } else {
                scores[studentId]?.add(Triple(name, score, date))
            }
            Log.d("StudentScore",scores.toString())
        }
        cursor.close()
        db.close()
        return scores
    }

}
