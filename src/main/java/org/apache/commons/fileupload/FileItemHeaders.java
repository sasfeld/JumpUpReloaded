/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.apache.commons.fileupload;

import java.util.Iterator;

public abstract interface FileItemHeaders
{
    public abstract String getHeader(String paramString);

    public abstract Iterator<String> getHeaders(String paramString);

    public abstract Iterator<String> getHeaderNames();
}
