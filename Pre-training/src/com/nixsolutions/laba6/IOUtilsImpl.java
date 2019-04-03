package com.nixsolutions.laba6;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import interfaces.task6.IOUtils;


public class IOUtilsImpl implements  IOUtils{

    public IOUtilsImpl() {}

    public void copyFile(String original, String copied) {
        File file1 = new File(original);
        File file2 = new File(copied);
        if (file1 == null || file2 == null) {
            throw new NullPointerException();
        }

        try (FileInputStream fin = new FileInputStream(original);
                FileOutputStream fout = new FileOutputStream(copied)) {
            int i;
            while ((i = fin.read()) != -1 ) {
                fout.write(i);
            }

        } catch (IOException exc) {
            throw new IllegalArgumentException(exc);
        }
    }
    @Override
    public void copyFileBuffered(File original, File copied) {
        if (original == null || copied == null) {
            throw new NullPointerException();
        }
        try (InputStream in = new BufferedInputStream(
                new FileInputStream(original));

                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(copied))) {

            int byte_;
            while ((byte_ = in.read()) != -1) {
                out.write(byte_);
            }

        } catch (IOException exc) {
            throw new IllegalArgumentException(exc);
        }    
    }

    @Override
    public void copyFileBest(String sourceFile, String targetFile) {
        File file = new File(sourceFile);
        File oFile = new File(targetFile);
        if (file == null || oFile == null) {
            throw new NullPointerException();
        }
        try (FileInputStream inChannel = new FileInputStream(sourceFile); 
                FileOutputStream outChannel = new FileOutputStream(targetFile)) {
            FileChannel f = inChannel.getChannel();
            FileChannel f2 = outChannel.getChannel();
            ByteBuffer buf = ByteBuffer.allocateDirect(8 * 1024);
            while (f.read(buf) != -1) {
                buf.flip();
                f2.write(buf);
                buf.clear();
            }
        } catch (Exception exc) {
            throw new IllegalArgumentException(exc);
        }
    }


    public String[] findFiles(String file)  {
        if (file == null) {
            throw new NullPointerException();
        }
        if (file == "") {
            throw new IllegalArgumentException();
        }
        String[] str = null ;
        Path dir = Paths.get(file);
        try (Stream<Path> walk = Files.walk(dir)) {
            List<String> arr = walk.filter(Files::isRegularFile)
                    .map(entry -> String.valueOf(entry.toAbsolutePath()))
                    .collect(Collectors.toList());
            str = arr.stream().toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }



    public String[] findFiles(String url, String ext)  {
        if (url == "") {
            throw new IllegalArgumentException();
        }
        String[] str = null ;

        Path dir = Paths.get(url);
        try (Stream<Path> walk = Files.walk(dir)) {
            List<String> arr = walk.filter(p -> p.toString().endsWith(
                    Optional.ofNullable(ext).orElse("")))
                    .filter(Files::isRegularFile)
                    .map(entry -> String.valueOf(entry.toAbsolutePath()))
                    .collect(Collectors.toList());
            str = arr.stream().toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }



    public void replaceChars(Reader in, Writer out, String inChars, String outChars) {
        if (in == null || out == null) {
            throw new NullPointerException();

        } else  if (inChars == null || outChars== null ) {
            inChars ="";
            outChars = "";
        }
        else if (inChars.length() != outChars.length()) {
            throw new IllegalArgumentException();
        }
        Map<Character, Character> map = new HashMap<Character, Character>();
        for (int i = 0; i < inChars.length(); i++) {
            map.put(inChars.charAt(i), outChars.charAt(i));
        }
        Set<Character> keys = map.keySet();
        int intch;
        try {
            while ((intch = in.read()) != -1) {
                Character ch = (char) intch;
                if (keys.contains(ch)) {
                    ch = map.get(ch);
                }
                out.write(ch);
            }
        } catch (IOException exc) {
            exc.printStackTrace(System.err);
        }
    }


    public static void main(String[] args) {
        try {
            IOUtilsImpl c = new IOUtilsImpl();
            c.copyFileBest("temp1.txt" ,"temp2.txt");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    } 
}