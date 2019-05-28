package io;

import annotations.Entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IO_Helper {

    public static List<String> getEntityClassnames(Path dirPath) {
        List<String> classnames = new ArrayList<String>();
        File dir = new File(dirPath.toString());
        File[] dirContent = dir.listFiles(); //alle FileObjekte die dort drin sind also keine Ordner

        for (File file : dirContent
        ) {
            if (file.isDirectory()) {
                classnames.addAll(getEntityClassnames(file.toPath()));
            } else if (file.isFile()) {
                String pathname = file.getAbsolutePath();
                if (pathname.endsWith(".java")) {
                    String classname = pathname.substring(pathname.indexOf("java") + 5, pathname.indexOf(".java")).replace(File.separator, ".");
                    try {
                        Class entityClass = Class.forName(classname);
                        if (entityClass.isAnnotationPresent(Entity.class)) {
                            classnames.add(classname);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
//                try {
//                    Class entitiyClass = Class.forName(file.getAbsolutePath());
//                    if(entitiyClass.isAnnotationPresent(Entity.class)){
//                        classname.add(file.getAbsolutePath());
//                    }
//                } catch (ClassNotFoundException e) {
//                    System.out.println("test");
//                }
//
//            }
        }
        return classnames;
    }

    public static void main(String[] args) {
        Path dirPath = Paths.get(".", "src", "main", "java");
        for (String str :
                getEntityClassnames(dirPath)) {
//            StringBuffer sb = new StringBuffer("");
//            sb.append(str.substring(str.indexOf("\\java\\") + 6));
//            String classname = sb.substring(sb.lastIndexOf("\\") + 1, sb.lastIndexOf("."));
//            sb = new StringBuffer(sb.substring(0, sb.indexOf("\\")));
//            sb.append("." + classname);
            System.out.println(str);
        }
    }
}
