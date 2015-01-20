/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.apache.commons.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public abstract interface FileItem extends Serializable, FileItemHeadersSupport
{
    public abstract InputStream getInputStream() throws IOException;

    public abstract String getContentType();

    public abstract String getName();

    public abstract boolean isInMemory();

    public abstract long getSize();

    public abstract byte[] get();

    public abstract String getString(String paramString)
            throws UnsupportedEncodingException;

    public abstract String getString();

    public abstract void write(File paramFile) throws Exception;

    public abstract void delete();

    public abstract String getFieldName();

    public abstract void setFieldName(String paramString);

    public abstract boolean isFormField();

    public abstract void setFormField(boolean paramBoolean);

    public abstract OutputStream getOutputStream() throws IOException;
}
