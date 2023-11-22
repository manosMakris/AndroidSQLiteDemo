package gr.hua.dit.it2021052.sqlite_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "USERS_DB";
    public static final String USER_TABLE = "USERS";
    public static final String USER_FIRST_NAME_FIELD = "fname";
    public static final String USER_LAST_NAME_FIELD = "lname";
    public SqliteDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (%s text, %s text);",
                USER_TABLE,
                USER_FIRST_NAME_FIELD,
                USER_LAST_NAME_FIELD);
        sqLiteDatabase.execSQL(query);
    }

    public void insertUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_FIRST_NAME_FIELD, user.getFirstName());
        values.put(USER_LAST_NAME_FIELD, user.getLastName());

        db.insert(USER_TABLE, null, values);
    }

    public List<UserModel> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s;", USER_TABLE);
        Cursor cursorUsers
                = db.rawQuery(query, null);

        ArrayList<UserModel> usersArrayList
                = new ArrayList<>();

        if (cursorUsers.moveToFirst()) {
            do {
                usersArrayList.add(new UserModel(cursorUsers.getString(0),
                                                 cursorUsers.getString(1)));
            } while (cursorUsers.moveToNext());
        }
        cursorUsers.close();
        return usersArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean deleteUser(UserModel userToDelete) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = String.format("%s=? and %s=?", USER_FIRST_NAME_FIELD, USER_LAST_NAME_FIELD);
        String[] deleteValues = {userToDelete.getFirstName(), userToDelete.getLastName()};

        return db.delete(USER_TABLE, whereClause, deleteValues) > 0;
    }
}
