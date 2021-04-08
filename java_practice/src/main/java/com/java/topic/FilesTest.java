package com.java.topic;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class FilesTest {
    //1、File类:文件和目录路径名的抽象表示形式，创建File对象后，仅仅是一个路径的表示，不代码具体的事物一定是存在的。

    /*
     * 2、构造方法:
     * public File(String pathname)：根据一个路径得到File对象
     * public File(String parent,String child)：根据一个目录和一个子文件/目录得到File对象
     * public File(File parent,String child)：根据一个父File对象和一个子文件/目录得到File对象
     */
    @Test
    public void testFile() {
        //File(String pathname)：根据一个路径得到File对象
        File file = new File("/Users/lina/practice/java_practice/file.txt");

        //File(String parent, String child):根据一个目录和一个子文件/目录得到File对象
        File file2 = new File("/Users/lina/practice/java_practice", "file2.txt");

        //File(File parent, String child):根据一个父File对象和一个子文件/目录得到File对象
        File file3 = new File("/Users/lina/practice/java_practice");
        File file4 = new File(file3, "file3.txt");

        //以上三种方式其实效果一样，常用第一种
    }

    /*
     * 3、创建功能
     * public boolean createNewFile()：当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。
     * public boolean mkdir()：创建此抽象路径名指定的目录。
     * public boolean mkdirs()：创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
     */
    @Test
    public void testFile2() {
        //需求：在某目录下创建一个文件夹test
        File file = new File("/Users/lina/practice/java_practice/test");
        //第一次执行返回true，第二次执行返回false,如果存在这样的文件夹就不创建了
        System.out.println("mkdir:" + file.mkdir());

        // 需求:在某目录test文件夹下创建一个文件a.txt
        File file2 = new File("/Users/lina/practice/java_practice/test/a.txt");
        try {
            System.out.println("createNewFile: " + file2.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 需求：在某目录demo文件夹下创建一个文件b.txt
        // Exception in thread "main" java.io.IOException: 系统找不到指定的路径。
        // 注意：要想在某个目录下创建内容，该目录首先必须存在。

        // mkdirs()：创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        File file3 = new File("/Users/lina/practice/java_practice/demo/a.txt");
        System.out.println("mkdirs:" + file3.mkdirs());
    }

    /**
     * 4、删除功能
     * public boolean delete()
     * 要删除一个文件夹，请注意该文件夹内不能包含文件或者文件夹
     */
    @Test
    public void testFile3() {
        // 创建文件
        File file = new File("/Users/lina/practice/java_practice/demo/a.txt");
        try {
            System.out.println("createNewFile: " + file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除功能：删除a.txt这个文件
        File file2 = new File("/Users/lina/practice/java_practice/demo/a.txt");
        System.out.println("delete a.txt: " + file2.delete());

        // 删除功能：删除demo这个文件夹
        File file3 = new File("/Users/lina/practice/java_practice/demo/a.txt");

        // 创建文件
        File file4 = new File("/Users/lina/practice/java_practice/demo/a.txt");
        try {
            System.out.println("createNewFile: " + file4.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除功能：删除test文件夹
        File file5 = new File("/Users/lina/practice/java_practice/demo");
        System.out.println("delete test文件夹: " + file5.delete());
        //test文件夹下面有文件或文件夹，不能删除
    }

    /**
     * 5、重命名功能
     * public boolean renameTo(File dest)
     * 如果路径名相同，就是改名。
     * 如果路径名不同，就是改名并剪切。
     */
    @Test
    public void testFile4() {
        // 创建一个文件对象
        File file = new File("/Users/lina/practice/java_practice/demo/b.txt");
        try {
            System.out.println("createNewFile: " + file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 需求：修改这个文件的名称为"c.txt"
        File file2 = new File("/Users/lina/practice/java_practice/demo/c.txt");
        System.out.println("renameTo: " + file.renameTo(file2));
    }

    /*
     * 6、判断功能
     * public boolean isDirectory()：判断是否是目录
     * public boolean isFile()：判断是否是文件
     * public boolean exists()：判断是否存在
     * public boolean canRead()：判断是否可读
     * public boolean canWrite()：判断是否可写
     * public boolean isHidden()：判断是否隐藏
     */
    @Test
    public void testFile5() {
        // 创建文件对象
        File file = new File("/Users/lina/practice/java_practice/demo/a.txt");
        try {
            System.out.println("createNewFile:" + file.createNewFile());//true
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("isDirectory:" + file.isDirectory());// false
        System.out.println("isFile:" + file.isFile());// true
        System.out.println("exists:" + file.exists());// true
        System.out.println("canRead:" + file.canRead());// true
        System.out.println("canWrite:" + file.canWrite());// true
        System.out.println("isHidden:" + file.isHidden());// false
    }

    /*
     * 7、基本获取功能
     * public String getAbsolutePath()：获取绝对路径
     * public String getPath()：获取相对路径
     * public String getName()：获取名称
     * public long length()：获取长度。字节数
     * public long lastModified()：获取最后一次的修改时间，毫秒值
     */
    @Test
    public void testFile6() {
        // 创建文件对象
        File file = new File("/Users/lina/practice/java_practice/demo/a.txt");
        //public String getAbsolutePath()：获取绝对路径
        System.out.println("getAbsolutePath:" + file.getAbsolutePath());
        //public String getPath():获取相对路径
        System.out.println("getPath:" + file.getPath());
        //public String getName():获取名称
        System.out.println("getName:" + file.getName());
        //public long length():获取长度。字节数
        System.out.println("length:" + file.length());
        //public long lastModified():获取最后一次的修改时间，毫秒值
        System.out.println("lastModified:" + file.lastModified());
        Date d = new Date(file.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(d);
        System.out.println(s);
    }

    /*
     * 8、高级获取功能
     * public String[] list()：获取指定目录下的所有文件或者文件夹的名称数组
     * public File[] listFiles()：获取指定目录下的所有文件或者文件夹的File数组
     */
    @Test
    public void testFile7() {
        // 指定一个目录
        File file = new File("/Users/lina/practice/java_practice/demo");

        //public String[] list():获取指定目录下的所有文件或者文件夹的名称数组
        String[] strArray = file.list();
        for (String s : strArray) {
            System.out.println(s);
        }
        System.out.println("------------");

        // public File[] listFiles():获取指定目录下的所有文件或者文件夹的File数组
        File[] fileArray = file.listFiles();
        for (File f : fileArray) {
            System.out.println(f.getName());
        }
    }

    /*
     * 9、练习题：判断某目录下是否有后缀名为.txt的文件，如果有，就输出此文件名称
     */
    @Test
    public void exerFile() {
        // 封装判断目录
        File file = new File("/Users/lina/practice/java_practice/demo");
        // 获取该目录下所有文件或者文件夹的File数组
        File[] fileArray = file.listFiles();
        // 遍历该File数组，得到每一个File对象，然后判断
        for (File f : fileArray) {
            // 是否是文件
            if (f.isFile()) {
                // 继续判断是否以.txt结尾
                if (f.getName().endsWith(".txt")) {
                    // 就输出该文件名称
                    System.out.println(f.getName());
                }
            }
        }
    }

    /*
     * 10、练习题：列出指定目录中所有的子文件名与所有的子目录名
     */
    @Test
    public void exerFile2() {
        // 创建File对象，表示这个目录
        File dir = new File("/Users/lina/practice/java_practice/demo");

        //通过listFiles方法得到所包含的所有子目录与子文件名称
        File[] names = dir.listFiles();

        //分别显示文件名与文件夹名
        for (int i = 0; i < names.length; ++i) {
            File file = names[i];
            if (file.isFile()) {
                System.out.println(("子文件："));
                System.out.println("\t" + file.getName());
            } else if (file.isDirectory()) {
                System.out.println(("子目录："));
                System.out.println("\t" + file.getName());
            }
        }
    }

    /*
     * 11、练习题：列出指定目录中所有的子文件名与所有的子目录名，要求目录名与文件名分开列出
     */
    @Test
    public void exerFile3() {
        //1创建File对象表示这个目录
        File dir = new File("/Users/lina/practice/java_practice/demo");

        //通过listFiles方法得到所包含的所有子目录与子文件名称
        File[] names = dir.listFiles();

        //得到所有的文件名集合，与所有的文件夹名集合
        List<File> filesList = new ArrayList<File>();
        List<File> dirsList = new ArrayList<File>();

        for (int i = 0; i < names.length; i++) {
            File file = names[i];
            if (file.isFile()) {
                filesList.add(file);
            } else if (file.isDirectory()) {
                dirsList.add(file);
            }
        }

        //分别显示文件名与文件夹名
        System.out.println("子文件：");
        for (int i = 0; i < filesList.size(); i++) {
            System.out.println("\t" + filesList.get(i).getName());
        }

        System.out.println("子目录：");
        for (int i = 0; i < dirsList.size(); i++) {
            System.out.println("\t" + dirsList.get(i).getName());
        }
    }

    /*
     * Java7中文件IO发生了很大的变化，专门引入了很多新的类:
     * import java.nio.file.DirectoryStream;
     * import java.nio.file.FileSystem;
     * import java.nio.file.FileSystems;
     * import java.nio.file.Files;
     * import java.nio.file.Path;
     * import java.nio.file.Paths;
     * import java.nio.file.attribute.FileAttribute;
     * import java.nio.file.attribute.PosixFilePermission;
     * import java.nio.file.attribute.PosixFilePermissions;
     * ......等等，来取代原来的基于java.io.File的文件IO操作方式.
     */

    //1. Path就是取代File的。Path用于来表示文件路径和文件。可以有多种方法来构造一个Path对象来表示一个文件路径，或者一个文件
    @Test
    public void testPaths() {
        //1.1 final类Paths的两个static方法从一个路径字符串来构造Path对象
        Path path = Paths.get("/Users/lina/practice/java_practice/demo", "a.txt");
        Path path2 = Paths.get("/Users/lina/practice/java_practice/demo/b.txt");
        URI u = URI.create("file:////Users/lina/practice/java_practice/test");//不要Missing scheme file:///
        Path path3 = Paths.get(u);

        //1.2 FileSystems构造
        Path path4 = FileSystems.getDefault().getPath("/Users/lina/practice/java_practice/demo", "c.txt");

        //1.3 File和Path之间的转换
        File file = new File("/Users/lina/practice/java_practice/demo/d.txt");
        Path path5 = file.toPath();
        File file2 = path5.toFile();

        //1.4 File和URI之间的转换
        URI uri = file.toURI();

        //1.5 创建一个文件
        Path target = Paths.get("/Users/lina/practice/java_practice/demo/e.txt");
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");

        try {
            if (!Files.exists(target)) {
                Files.createFile(target);
                Files.setPosixFilePermissions(target, perms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1.6 Files.newBufferedReader读取文件
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("/Users/lina/practice/java_practice/demo/e.txt"), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1.7 Files.newBufferedWriter写文件
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("/Users/lina/practice/java_practice/demo/e.txt"), StandardCharsets.UTF_8);
            writer.write("你好java");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1.8 遍历一个文件夹
        Path dir = Paths.get("/Users/lina/practice/java_practice/demo");
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream(dir);
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Stream<Path> s = Files.list(Paths.get("/Users/lina/practice/java_practice/demo"));
            Iterator<Path> ite = s.iterator();
            while (ite.hasNext()) {
                Path p = ite.next();
                System.out.println(p.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //1.9 使用Files.walkFileTree遍历整个文件目录
        Path startDir = Paths.get("/Users/lina/practice/java_practice");
        List<Path> result = new LinkedList<>();
        try {
            Files.walkFileTree(startDir, new FindJavaVistor(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("size: " + result.size());
        for (Path path : result) {
            System.out.println(path);
        }
    }

    private static class FindJavaVistor extends SimpleFileVisitor<Path> {
        private List<Path> result;

        public FindJavaVistor(List<Path> result) {
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.toString().endsWith(".java")) {
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }

    //2. 强大的java.nio.file.Files
    //2.1 创建目录和文件,创建目录和文件Files.createDirectories 和 Files.createFile不能混用，必须先有目录，才能在目录中创建文件。
    @Test
    public void testFiles() {
        //先创建目录，再创建文件
        try {
            Path path = Paths.get("/Users/lina/practice/java_practice/test");
            Files.createDirectory(path);
            if (Files.exists(path)) {
                Files.createFile(Paths.get("/Users/lina/practice/java_practice/test/a.txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //在不存在的目录下创建文件失败 java.nio.file.NoSuchFileException: /Users/lina/practice/java_practice/test2/a.txt
        try {
            Path path = Paths.get("/Users/lina/practice/java_practice/test2/a.txt");
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 2.2 文件复制
     * 从文件复制到文件：Files.copy(Path source, Path target, CopyOption options);
     * 从输入流复制到文件：Files.copy(InputStream in, Path target, CopyOption options);
     * 从文件复制到输出流：Files.copy(Path source, OutputStream out);
     */
    @Test
    public void testFiles2() {
        try {
            Path path = Paths.get("/Users/lina/practice/java_practice/test");
            Path sourceFile = Paths.get("/Users/lina/practice/java_practice/test/b.txt");
            Path targetFile = Paths.get("/Users/lina/practice/java_practice/test/target.txt");
            Path targetFile2 = Paths.get("/Users/lina/practice/java_practice/test/target2.txt");
            Files.createDirectory(path);
            if (Files.exists(path)) {
                Files.createFile(sourceFile);
                BufferedWriter writer = Files.newBufferedWriter(sourceFile, StandardCharsets.UTF_8);
                writer.write("hello world");
                writer.flush();
                writer.close();

                //从文件复制到文件
                Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);

                //从输入流复制到文件
                Files.copy(System.in, targetFile2);

                //从文件复制到输出流
                Files.copy(sourceFile, System.out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //2.3 读取文件属性
    @Test
    public void testFiles3() {
        Path zip = Paths.get("/Users/lina/practice/java_practice/test/target.txt");
        try {
            System.out.println(Files.getLastModifiedTime(zip));
            System.out.println(Files.size(zip));
            System.out.println(Files.isSymbolicLink(zip));
            System.out.println(Files.isDirectory(zip));
            System.out.println(Files.readAttributes(zip, "*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //2.4 读取和设置文件权限
    @Test
    public void testFiles4() {
        Path profile = Paths.get("/Users/lina/practice/java_practice/test/target.txt");
        PosixFileAttributes attrs = null;// 读取文件的权限
        try {
            attrs = Files.readAttributes(profile, PosixFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<PosixFilePermission> posixPermissions = attrs.permissions();
        posixPermissions.clear();
        String owner = attrs.owner().getName();
        String perms = PosixFilePermissions.toString(posixPermissions);
        System.out.format("%s %s%n", owner, perms);

        posixPermissions.add(PosixFilePermission.OWNER_READ);
        posixPermissions.add(PosixFilePermission.GROUP_READ);
        posixPermissions.add(PosixFilePermission.OTHERS_READ);
        posixPermissions.add(PosixFilePermission.OWNER_WRITE);

        try {
            Files.setPosixFilePermissions(profile, posixPermissions);    // 设置文件的权限
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
