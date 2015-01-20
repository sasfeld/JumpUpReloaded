/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.apache.commons.fileupload;

public abstract interface FileItemFactory
{
    public abstract FileItem createItem(String paramString1,
            String paramString2, boolean paramBoolean, String paramString3);
}
