package oms.io;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import oms.ability.ModAbility;

public class File extends ModAbility {
     private java.io.File _File;
     private String _path,_realPath = null;
     public static final String UTF8 = "UTF-8";
     public static final String GBK = "GBK";
     public static final String UTF32 = "UTF-32";
     public static final String UTF16 = "UTF-16";

     public boolean mkdirs(){
          return this._File.mkdirs();
     }

     public File(String string){
          this._path = string;
          this._realPath = getPath(_path,getContext());
          if(!_path.startsWith("@")){
               this._File = new java.io.File(this._realPath);
          }
     }
     //读取文件
     public String read(){
          try {
               return read(getInputStream());
          } catch (IOException e) {
               e.printStackTrace();
               return null;
          }
     }

     public void write(String sequence){
          if(_path.startsWith("@")){
               return;
          }
          write(sequence,"-");
     }



     public boolean move(String o){
          if(_path.startsWith("@")||_path.startsWith("@")){
               return false;
          }else{
               java.io.File file = new java.io.File(getPath(o,getContext()));
               if(copy(o)){
                    file.delete();
               }
               return false;
          }
     }

     public boolean copy(String f){
          if(f.startsWith("@")){
               return false;
          }else{
               java.io.File file = new java.io.File(getPath(f,getContext()));
               if(file.getParentFile()!=null){
                    file.getParentFile().mkdirs();
               }else{
                    file.mkdirs();
               }
               try {
                    FileOutputStream stream = new FileOutputStream(file);
                    byte[] b = new byte[1024];
                    //用来接收每次读到的字节数量；
                    int len = -1;
                    InputStream inputStream = getInputStream();
                    //read(byte[]) 读取一定数量的字节也就是参数设置的大小 放到缓存区 返回每次读取的字节数量   read() 返回每次读取到的字节；
                    while ((len=inputStream.read(b))!=-1){
                         //将缓存区的字节输出到目标文件 因为文件末尾读到的字节数不确定所以 每次输出缓存区的 0 到 实际读到的字节长度；
                         stream.write(b,0,len);
                    }
                    //因为输入出流用到系统级资源 所以要关流释放资源；
                    inputStream.close();
                    stream.close();
                    return true;
               } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
               } catch (IOException e) {
                    e.printStackTrace();
                    return false;
               }
          }
     }



     public void write(String sequence,String s){
          java.io.File file = new java.io.File(_realPath);
          if(file.getParentFile()!=null){
               file.getParentFile().mkdirs();
          }else{
               file.mkdirs();
          }
          byte[] contentInBytes = null;
          try (FileOutputStream fop = new FileOutputStream(file)) {
               if (!file.exists()) {
                    file.createNewFile();
               }else{
                    if(s == "-"){
                         contentInBytes = sequence.getBytes();
                    }else if(s == "+"){
                         contentInBytes = (read()+sequence).getBytes();
                    }else if(s == "++"){
                         contentInBytes = (read()+"\n"+sequence).getBytes();
                    }
               }
               fop.write(contentInBytes);
               fop.flush();
               fop.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     private static String read(InputStream stream){
          if(stream == null){
               return null;
          }
          try {
               // 把每次读取的内容写入到内存中，然后从内存中获取
               ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
               byte[] buffer = new byte[1024];
               int len = 0;
               // 只要没读完，不断的读取
               while ((len = stream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
               }
               // 得到内存中写入的所有数据
               byte[] data = outputStream.toByteArray();
               outputStream.close();
               stream.close();
               return new String(data,UTF8);
               //return new String(data, "GBK");//以GBK（什么编码格式）方式转
          } catch (FileNotFoundException e) {
               e.printStackTrace();
          } catch (IOException e) {
               e.printStackTrace();
          }
          return null;
     }

     public String getSuffix() {
          return _path.substring(_path.lastIndexOf(".") + 1);
          //return suffix;
     }

     public boolean isFile(){
          if(_File!=null){
               return _File.isFile();
          }
          return false;
     }

     public boolean isDirectory(){
          if(_File!=null){
               return _File.isDirectory();
          }
          return false;
     }

     public boolean exists(){
          if(_File!=null){
               return _File.exists();
          }
          return false;
     }

     public String getName(){
          if(_File!=null) {
               return _File.getName();
          }
          return _path.substring(_path.lastIndexOf("/") + 1,_path.length());
     }


     public InputStream getInputStream() throws IOException {
          if(_path.startsWith("@")){
               return getContext().getAssets().open(_path.substring(1));
          }
          return new FileInputStream(_File);
     }

     private String[] h(Context context, String str) {
          if (str.startsWith("@")) {
               try {
                    return context.getAssets().list(str.substring(1));
               } catch (IOException e) {
                    e.printStackTrace();
                    return null;
               }
          }
          java.io.File stringBuffer = new java.io.File(_realPath);
          return stringBuffer.exists() ? stringBuffer.list() : null;
     }

     public String getPath(){
          return _realPath;
     }

     private static String a = Environment.getExternalStorageDirectory().toString();
     String getPath(String str, Context context){
          StringBuilder stringBuilder;
          if (str.startsWith("@")) {
               stringBuilder = new StringBuilder();
               stringBuilder.append("assets/");
               stringBuilder.append(str.substring(1));
               return stringBuilder.toString();
          }
          String str2 = "/";
          if (str.startsWith("%")) {
               str = str.substring(1);
               if (str.indexOf(92) != -1) {
                    str = str.replace('\\', '/');
               }
               if (str.startsWith(str2)) {
                    return str;
               }
               if (Build.VERSION.SDK_INT >= 30) {
                    return String.format("%s/%s", new Object[]{context.getExternalFilesDir(""), str});
               }
               stringBuilder = new StringBuilder();
               stringBuilder.append(a);
               stringBuilder.append('/');
               stringBuilder.append(str);
               return stringBuilder.toString();
          } else if (str.startsWith("$")) {
               StringBuilder stringBuilder2 = new StringBuilder();
               stringBuilder2.append(context.getFilesDir().getPath() + "/");
               stringBuilder2.append(str.substring(1));
               return stringBuilder2.toString();
          } else if (str.startsWith(str2)) {
               return str;
          } else if(str.startsWith("#")){
               return str;
          } else {
               stringBuilder = new StringBuilder();
               stringBuilder.append(a);
               stringBuilder.append('/');
               stringBuilder.append(str);
               return stringBuilder.toString();
          }
     }
     public boolean delete(){
          if(_path.startsWith("@")||_path.startsWith("#")){
               return false;
          }
          try {
               Runtime.getRuntime().exec("rm -rf "+_realPath);
          } catch (IOException e) {
               e.printStackTrace();
          }
          if(this._File.exists()){
               return true;
          }else {
               return false;
          }
     }
}
