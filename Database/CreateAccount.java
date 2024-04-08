package com.example.project_1.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.project_1.SeatSelectActivity;

public class CreateAccount extends SQLiteOpenHelper  {
    int trangthai;
//    public static String DATABASE_NAME = "CINEMA";
//    public static int DATABASE_VERSION = 1;
    public static String TB_KHACHHANG = "KhachHang";

    public static String TB_KHACHHANG_MAKH = "MAKH";
    public static String TB_KHACHHANG_HOTEN = "HOTEN";
//    public static String TB_KHACHHANG_NGAYSINH = "NGAYSINH";
    public static String TB_KHACHHANG_SDT = "SDT";
    public static String TB_KHACHHANG_TENDN = "TENDN";
    public static String TB_KHACHHANG_MATKHAU = "MATKHAU";
    public static String TB_KHACHHANG_ROLE = "ROLE";
    public static String CREATE_TABLE = "CREATE TABLE "
            + TB_KHACHHANG + "( " + TB_KHACHHANG_MAKH + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TB_KHACHHANG_HOTEN + " TEXT, "
            + TB_KHACHHANG_SDT + " TEXT, "
            + TB_KHACHHANG_TENDN + " TEXT, "
            + TB_KHACHHANG_MATKHAU + " TEXT, "
            + TB_KHACHHANG_ROLE + " INTEGER);";

    public static String TB_FILM = "Phim";
    public static String TB_FILM_MAPHIM = "MAPHIM";
    public static String TB_FILM_TENPHIM = "TENPHIM";
    public static String TB_FILM_THOIGIAN = "THOIGIAN";
    public static String TB_FILM_HINHANH = "HINHANH";
    public static String TB_FILM_DINHDANG = "MADD";
    public static String TB_FILM_THELOAI = "MATL";
    public static String TB_FILM_DESC = "MOTA";
    public static String TB_FILM_STATUS = "TRANGTHAI";

    public static String CREATE_TABLE_FILM = "CREATE TABLE "
            + TB_FILM + "( " + TB_FILM_MAPHIM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TB_FILM_TENPHIM + " TEXT, "
            + TB_FILM_THOIGIAN + " TEXT, "
            + TB_FILM_DINHDANG + " TEXT, "
            + TB_FILM_THELOAI + " TEXT, "
            + TB_FILM_DESC + " TEXT, "
            + TB_FILM_STATUS + " NUMERIC, "
            + TB_FILM_HINHANH + " BLOB);";
//            + TB_FILM_TRAILER + " TEXT); ";

    public static String TB_DINHDANG = "DINHDANG";
    public static String TB_DINHDANG_MADD = "MADD";
    public static String TB_DINHDANG_TENDD = "TENDD";
    public static String CREATE_TABLE_DINHDANG = "CREATE TABLE "
            + TB_DINHDANG + " ( " + TB_DINHDANG_MADD + " TEXT PRIMARY KEY, "
            + TB_DINHDANG_TENDD + " TEXT);";

    public static String TB_THELOAI = "THELOAI";
    public static String TB_THELOAI_MATL = "MATL";
    public static String TB_THELOAI_TENTL = "TENTL";
    public static String CREATE_TABLE_THELOAI = "CREATE TABLE "
            + TB_THELOAI + " ( " + TB_THELOAI_MATL + " TEXT PRIMARY KEY, "
            + TB_THELOAI_TENTL + " TEXT);";

    public static String TB_PHONGCHIEU = "PHONGCHIEU";
    public static String TB_PHONGCHIEU_MAPHONG = "MAPHONG";
    public static String TB_PHONGCHIEU_TENPHONG = "TENPHONG";
    public static String TB_PHONGCHIEU_SOLUONGGHE = "SOLUONGGHE";
    public static String CREATET_TABLE_PHONGCHIEU = "CREATE TABLE " + TB_PHONGCHIEU + " ( "
            + TB_PHONGCHIEU_MAPHONG + " TEXT PRIMARY KEY, "
            + TB_PHONGCHIEU_TENPHONG + " TEXT, "
            + TB_PHONGCHIEU_SOLUONGGHE + " INTEGER);";

    public static String TB_LOAIVE = "LOAIVE";
    public static String TB_LOAIVE_MALV = "MALV";
    public static String TB_LOAIVE_TENLV = "TENLV";
    public static String TB_LOAIVE_DONGIA = "DONGIA";

    public static String CREATE_TABLE_LOAIVE = "CREATE TABLE " + TB_LOAIVE + " ( "
            + TB_LOAIVE_MALV + " TEXT PRIMARY KEY, "
            + TB_LOAIVE_TENLV + " TEXT, "
            + TB_LOAIVE_DONGIA + " INTEGER);";


    public static String TB_XUATCHIEU = "XUATCHIEU";
    public static String TB_XUATCHIEU_MAXC = "MAXC";
    public static String TB_XUATCHIEU_GIOCHIEU = "GIOCHIEU";
    public static String TB_XUATCHIEU_MAPHIM = "MAPHIM";
    public static String TB_XUATCHIEU_MAPC = "MAPC";
    public static String CREATE_TABLE_XUATCHIEU = "CREATE TABLE IF NOT EXISTS " + TB_XUATCHIEU + " ( "
            + TB_XUATCHIEU_MAXC + " INTEGER PRIMARY KEY, "
            + TB_XUATCHIEU_GIOCHIEU + " TEXT, "
            + TB_XUATCHIEU_MAPHIM + " INTEGER, "
            + TB_XUATCHIEU_MAPC + " TEXT);";

    public static String TB_GHE = "GHE";
    public static String TB_GHE_ID = "ID";
    public static String TB_GHE_SOGHE = "SOGHE";
    public static String TB_GHE_MAXC = "MAXC";
    public static String TB_GHE_TRANGTHAI = "TRANGTHAI";
    public static String CREATE_TABLE_GHE = "CREATE TABLE " + TB_GHE + " ( "
            + TB_GHE_ID + " INTEGER PRIMARY KEY, "
            + TB_GHE_SOGHE + " INTEGER, "
            + TB_GHE_TRANGTHAI + " INTEGER, "
            + TB_GHE_MAXC + " INTEGER);";

    public static String TB_VE = "VE";
    public static String TB_VE_MAVE = "MAVE";
    public static String TB_VE_MAXC = "MAXC";
    public static String TB_VE_MAKH = "MAKH";
    public static String TB_VE_GHE = "GHE";
    public static String TB_VE_QR_VE = "QRVE";
    public static String CREATE_TABLE_VE = "CREATE TABLE " + TB_VE + " ( "
            + TB_VE_MAVE + " INTEGER PRIMARY KEY, "
            + TB_VE_MAXC + " INTEGER, "
            + TB_VE_MAKH + " INTEGER, "
            + TB_VE_GHE + " TEXT, "
            + TB_VE_QR_VE + " BLOB);";

    public CreateAccount(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Truy vấn không trả kết quả
    public void QueryData( String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    // Truy vấn trả kết quả

    public Cursor GetData(String sql ){
        SQLiteDatabase database = getReadableDatabase();
        return  database.rawQuery( sql , null );
    }



    // Thêm khách hàng khi đăng kí
    public String InsertCustomer( String hoten, String sdt, String tendn , String pass ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", makh);
        contentValues.put( TB_KHACHHANG_HOTEN , hoten);
//        contentValues.put("ngaysinh", ngaysinh);
        contentValues.put(TB_KHACHHANG_SDT, sdt );
        contentValues.put(TB_KHACHHANG_TENDN , tendn);
        contentValues.put(TB_KHACHHANG_MATKHAU, pass);
        String msg = "";
        if( db.insert(  TB_KHACHHANG ,null , contentValues) == -1 ) {
            msg = "Thêm thất bại";
        } else {
            msg = "Thêm thành công";
        }
        return msg;
    }



    // Xử lý khi đăng nhập
    public Cursor CheckLogin( String tendn , String pass ){
        int idkh = 0;
        String sql = "SELECT * FROM " + TB_KHACHHANG + " WHERE " + TB_KHACHHANG_TENDN + " = '" + tendn
                + "' AND " + TB_KHACHHANG_MATKHAU + " = '" + pass + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( sql , null );

        return  cursor;
    }

    public int GetStatusSeat( int idxc , int soghe){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TB_GHE + " WHERE " + TB_GHE_MAXC + " = " + idxc + " AND " + TB_GHE_SOGHE + " = " + soghe;
        Cursor cursor = db.rawQuery(sql, null );
        if(cursor.getCount() > 0 ){
            if( cursor.moveToFirst()){
                do {
                    int ketqua = cursor.getColumnIndex(TB_GHE_TRANGTHAI);
                    trangthai = cursor.getInt(ketqua);
                } while (cursor.moveToNext());
            }
        } else {
            trangthai = 0;
        }
        return trangthai;
    }
    public boolean InsertFilm( String tenphim, String thoiluong, String dinhdang, String theloai , String mota, int trangthai ,byte[] hinhanh ){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TB_FILM + " VALUES(null, ?, ?, ?, ?, ?, ?, ?);";
        SQLiteStatement sqLiteStatement = db.compileStatement( sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, tenphim);
        sqLiteStatement.bindString(2, thoiluong);
        sqLiteStatement.bindString(3, dinhdang);
        sqLiteStatement.bindString(4, theloai);
        sqLiteStatement.bindString(5, mota);
        sqLiteStatement.bindDouble(6 , trangthai);
        sqLiteStatement.bindBlob(7, hinhanh);
        long result =  sqLiteStatement.executeInsert();
        if( result == -1 ){
            return false;
        } else {
            return true;
        }
    }

    public boolean UpdateFilm( int idfilm, String tenphim, String thoiluong, String dinhdang, String theloai , String mota, int trangthai ,byte[] hinhanh ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_FILM_TENPHIM , tenphim);
        contentValues.put(TB_FILM_THOIGIAN , thoiluong);
        contentValues.put(TB_FILM_DINHDANG , dinhdang);
        contentValues.put(TB_FILM_THELOAI , theloai);
        contentValues.put(TB_FILM_DESC , mota);
        contentValues.put(TB_FILM_STATUS , trangthai);
        contentValues.put(TB_FILM_HINHANH , hinhanh);

        String whereCluase = TB_FILM_MAPHIM + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(idfilm)};
        int result = db.update(TB_FILM, contentValues, whereCluase, whereArgs);
        if( result == 0 ){
            return false;
        } else {
            return true;
        }
    }

    public boolean DeleteFilm(int idfilm){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = TB_FILM_MAPHIM + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(idfilm)};
        int result = db.delete(TB_FILM, whereClause,  whereArgs );
        if( result == 0 ){
            return  false;
        } else {
            return true;
        }
    }

    public boolean deleteXuatChieuPhim( int idfilm){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = TB_XUATCHIEU_MAPHIM + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(idfilm)};
        int result = db.delete(TB_XUATCHIEU , whereClause,  whereArgs );
        if( result == 0 ){
            return  false;
        } else {
            return true;
        }
    }
    public void InsertTicket( int maxc, int makh , String ghe, byte[] qrve ){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TB_VE + " VALUES(null, ?, ?, ?, ?);";
        SQLiteStatement sqLiteStatement = db.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindDouble(1, maxc);
        sqLiteStatement.bindDouble(2, makh);
        sqLiteStatement.bindString(3,ghe);
        sqLiteStatement.bindBlob(4, qrve);
        sqLiteStatement.executeInsert();

    }

    public  int GetTicket(int maxc, int makh , String ghe){
        int idve = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        String sql1 = "SELECT " + TB_VE_MAVE + " FROM " + TB_VE + " WHERE " + TB_VE_GHE + " = '" + ghe + "' AND " + TB_VE_MAKH + " = " + makh + " AND " + TB_VE_MAXC + " = " + maxc;
        Cursor cursor = database.rawQuery(sql1 , null);
        if( cursor.getCount() != 0 ){
            while (cursor.moveToNext()){
                int result = cursor.getColumnIndex(TB_VE_MAVE);
                idve = cursor.getInt(result);
            }
            return idve;
        } else {
            return 0;
        }
    }

    public Cursor GetTicketbyId( int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * " + " FROM " + TB_VE + " WHERE " + TB_VE_MAVE + " = " + id;
        Cursor cursor =  db.rawQuery(sql, null);
        return cursor;
    }
    public void UpdateQRcodeTicket( byte[] qrcode , int mave){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_VE_QR_VE , qrcode);
        String whereCluase = TB_VE_MAVE + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(mave)};
        db.update(TB_VE, contentValues, whereCluase, whereArgs);
    }
    public void InsertSeat( int soghe , int trangthai, int maxc){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TB_GHE + " VALUES( null, ?, ?, ?);";
        SQLiteStatement sqLiteStatement = db.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindDouble(1 , soghe);
        sqLiteStatement.bindDouble(2, trangthai);
        sqLiteStatement.bindDouble(3, maxc);
        sqLiteStatement.executeInsert();
    }
    public void XoaXuatChieu( int maxuatchieu){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = TB_GHE_MAXC + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(maxuatchieu)};
        int n = db.delete(TB_GHE, whereClause, whereArgs );
        if( n == 0 ){
            Log.d("SeatSelectActivity", "Đã xóa ");
        }
    }

    public byte[] getBlobImageFilm( int idfilm ){
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] blodData = new byte[0];
        String sql = "SELECT " + TB_FILM_HINHANH + " FROM " + TB_FILM + " WHERE " + TB_FILM_MAPHIM + " = " + idfilm;
        Cursor cursor = db.rawQuery(sql, null);
        if( cursor != null && cursor.moveToFirst()){
            blodData = cursor.getBlob(0);
            cursor.close();
        }
        return blodData;
    }

    @SuppressLint("Range")
    public Cursor GetSeat( String MAPC ){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT " + TB_PHONGCHIEU_SOLUONGGHE + " FROM " + TB_PHONGCHIEU + " WHERE " + TB_PHONGCHIEU_MAPHONG + " = '" + MAPC + "'";
        return  database.rawQuery( sql , null );
    }

    public Cursor GetPriceTicket(){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT " + TB_LOAIVE_DONGIA + " FROM " + TB_LOAIVE + " WHERE " + TB_LOAIVE_MALV + " = 'LV1'";
        return  database.rawQuery( sql , null );
    }

    public Cursor getFilmById( int id){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_FILM + " WHERE " + TB_FILM_MAPHIM + " = " + id;
        Cursor cursor = database.rawQuery(sql , null);
        return  cursor;
    }

    public Cursor getRoom( String id ){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT " + TB_PHONGCHIEU_TENPHONG + " FROM " + TB_PHONGCHIEU + " WHERE " + TB_PHONGCHIEU_MAPHONG + " = '" + id + "'";
        Cursor cursor = database.rawQuery(sql, null );
        return cursor;
    }

    @SuppressLint("Range")
    public Cursor GetXC(int id ){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT *  FROM " + TB_XUATCHIEU + " WHERE " + TB_XUATCHIEU_MAXC + " = " + id;
        Cursor cursor = database.rawQuery(sql, null );
        return cursor;
    }

    public Cursor getTicketbyId( int id){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_VE + " WHERE " + TB_VE_MAKH + " = " + id;
        Cursor cursor = database.rawQuery( sql, null );
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_FILM);
        db.execSQL(CREATE_TABLE_DINHDANG);
        db.execSQL(CREATE_TABLE_THELOAI);
        db.execSQL(CREATET_TABLE_PHONGCHIEU);
        db.execSQL(CREATE_TABLE_LOAIVE);
        db.execSQL(CREATE_TABLE_XUATCHIEU);
        db.execSQL(CREATE_TABLE_GHE);
        db.execSQL(CREATE_TABLE_VE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(CreateAccount.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TB_KHACHHANG);
        db.execSQL("DROP TABLE IF EXISTS " + TB_FILM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_DINHDANG);
        db.execSQL("DROP TABLE IF EXISTS " + TB_THELOAI);
        db.execSQL("DROP TABLE IF EXISTS " + TB_PHONGCHIEU);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOAIVE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_XUATCHIEU);
        db.execSQL("DROP TABLE IF EXISTS " + TB_GHE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_VE);
        onCreate(db);
    }


}
