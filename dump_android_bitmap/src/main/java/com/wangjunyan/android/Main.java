/*
 * Copyright 2005-2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package com.wangjunyan.android;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.eclipse.mat.parser.internal.SnapshotFactory;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.util.ConsoleProgressListener;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.parser.model.PrimitiveArrayImpl;

/*
 * Export all bitmaps from Android heap dump.
 */

public class Main {

    private static String VERSION_STRING =
            Main.class.getPackage().getSpecificationTitle() + " " +
            Main.class.getPackage().getSpecificationVersion();

    private static void usage(String message) {
        if (message != null) {
            System.err.println("ERROR: " + message);
        }
        System.err.println("Usage:  andromat [-version] [-h|-help] <file>");
        System.err.println();
        System.err.println("\t-version          Report version number");
        System.err.println("\t-h|-help          Print this help and exit");
        System.err.println("\t<file>            The file to read");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            usage("Arguments error!");
        }

        String arg = args[args.length - 1];

        if ("-v".equals(arg) || "-version".equals(arg)) {
            System.out.print(VERSION_STRING);
            System.exit(0);
        }
        if ("-h".equals(arg) || "-help".equals(arg)) {
            usage(null);
        }

        String fileName = arg;

        IProgressListener listener = new ConsoleProgressListener(System.out);

        SnapshotFactory sf = new SnapshotFactory();
        ISnapshot snapshot = sf.openSnapshot(new File(fileName),
                new HashMap<String, String>(), listener);

        System.out.println(snapshot.getSnapshotInfo());
        System.out.println();

        String[] classNames = {
            "android.graphics.Bitmap"
        };

        for (String name : classNames) {
            Collection<IClass> classes = snapshot.getClassesByName(name, false);
            if (classes == null || classes.isEmpty()) {
                System.out.println(String.format(
                        "Cannot find class %s in heap dump", name));
                continue;
            }
            assert classes.size() == 1;
            IClass clazz = classes.iterator().next();
            int[] objIds = clazz.getObjectIds();
            long minRetainedSize = snapshot.getMinRetainedSize(objIds, listener);
            System.out.println(String.format("%s instances = %d, retained size >= %d", clazz.getName(), objIds.length, minRetainedSize));
            for(int i = 0; i < objIds.length; i++)
            {
                IObject bmp = snapshot.getObject(objIds[i]);
                String address = Long.toHexString(snapshot.mapIdToAddress(objIds[i]));
                int height = ((Integer)bmp.resolveValue("mHeight")).intValue();
                int width = ((Integer)bmp.resolveValue("mWidth")).intValue();
                byte[] buffer;
                PrimitiveArrayImpl array = (PrimitiveArrayImpl)bmp.resolveValue("mBuffer");
                if((height<=0) || (width<=0))
                {
                    System.out.println(String.format("Bitmap address=%s has bad height %d or width %d!", address, height, width));
                    continue;
                }
                if(array == null)
                {
                    System.out.println(String.format("Bitmap address=%s has null buffer value!", address));
                    continue;
                }
                buffer = (byte[])array.getValueArray();
                int[] rgba = new int[width*height];
                for(int j = 0; j < width*height; j++)
                {
                    //convert byte[] [R][G][B][A] to int[] [ARGB]
                    rgba[j] = ((buffer[j*4]<<16) | (buffer[j*4+1]<<8) | (buffer[j*4+2]) | (buffer[j*4+3]<<24));
                }
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                image.setRGB(0, 0, width, height, rgba, 0, width);
                try{
                    File outputfile = new File("bmp_" + address + ".png");
                    ImageIO.write(image, "png", outputfile);
                }catch(IOException e){
                    e.printStackTrace();
                }
                System.out.println(String.format("id=%d, address=%s, height=%d, width=%d, size=%d", objIds[i], address, height, width, buffer.length));
            }
        }
    }
}
