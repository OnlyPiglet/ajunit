package file2class;

import log.LoggerHolder;

import java.io.*;

/**
 * @ProjectName: ajunit
 * @Package: PACKAGE_NAME
 * @ClassName: file2class.GenClassLoader
 * @Author: 吴成昊
 * @Description:
 * @Date: 2019/8/12 11:05
 * @Version: 0.1
 */
public class GenClassLoader extends ClassLoader {

    public Class<?> LoadClass(String absClassPath) throws ClassNotFoundException{
        return findMyClass(absClassPath);
    }

    protected Class<?> findMyClass(String name) throws ClassNotFoundException{

        try{

            byte [] classData = getData(name);

            if(classData==null || classData.length == 0){

            }
            else{
                //defineClass方法将字节码转化为类
                return this.defineClass(classData,0,classData.length);
            }

        }catch (IOException e){
            LoggerHolder.log.error(e.getMessage(),e);
        }

        return super.findClass(name);

    }

    private byte[] getData(String classAbsName) throws IOException{
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            String location = classAbsName;//.replace("\\",".");
            in=new FileInputStream(location);
            out=new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int len=0;
            while((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            return out.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            in.close();
            out.close();
        }
        return null;
    }


}