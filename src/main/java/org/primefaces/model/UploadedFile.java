/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.primefaces.model;

import java.io.IOException;
import java.io.InputStream;

public abstract interface UploadedFile
{
    public abstract String getFileName();

    public abstract InputStream getInputstream() throws IOException;

    public abstract long getSize();

    public abstract byte[] getContents();

    public abstract String getContentType();

    public abstract void write(String paramString) throws Exception;
}
