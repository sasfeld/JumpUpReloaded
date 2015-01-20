/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package de.htw.fb4.imi.jumpup.upload.filter;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.primefaces.webapp.MultipartRequest;

public class FileUploadFilter implements Filter
{
    private static final Logger logger = Logger
            .getLogger(FileUploadFilter.class.getName());
    private static final String THRESHOLD_SIZE_PARAM = "thresholdSize";
    private static final String UPLOAD_DIRECTORY_PARAM = "uploadDirectory";
    private String thresholdSize;
    private String uploadDir;
    private boolean bypass;

    public void init(FilterConfig filterConfig) throws ServletException
    {
        boolean isAtLeastJSF22 = detectJSF22();
        String uploader = filterConfig.getServletContext().getInitParameter(
                "primefaces.UPLOADER");
        if ((uploader == null) || (uploader.equals("auto")))
            this.bypass = (isAtLeastJSF22);
        else if (uploader.equals("native"))
            this.bypass = true;
        else if (uploader.equals("commons")) {
            this.bypass = false;
        }
        this.thresholdSize = filterConfig.getInitParameter("thresholdSize");
        this.uploadDir = filterConfig.getInitParameter("uploadDirectory");

        if (logger.isLoggable(Level.FINE))
            logger.fine("FileUploadFilter initiated successfully");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException
    {
        if (this.bypass) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        boolean isMultipart = ServletFileUpload
                .isMultipartContent(httpServletRequest);

        if (isMultipart) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Parsing file upload request");
            }
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            if (this.thresholdSize != null) {
                diskFileItemFactory.setSizeThreshold(Integer.valueOf(
                        this.thresholdSize).intValue());
            }
            if (this.uploadDir != null) {
                diskFileItemFactory.setRepository(new File(this.uploadDir));
            }

            FileCleaningTracker fileCleaningTracker = FileCleanerCleanup
                    .getFileCleaningTracker(httpServletRequest.getSession()
                            .getServletContext());
            if (fileCleaningTracker != null) {
                diskFileItemFactory.setFileCleaningTracker(fileCleaningTracker);
            }

            ServletFileUpload servletFileUpload = new ServletFileUpload(
                    diskFileItemFactory);
            MultipartRequest multipartRequest = new MultipartRequest(
                    httpServletRequest, servletFileUpload);

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("File upload request parsed succesfully, continuing with filter chain with a wrapped multipart request");
            }
            filterChain.doFilter(multipartRequest, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void destroy()
    {
        if (logger.isLoggable(Level.FINE))
            logger.fine("Destroying FileUploadFilter");
    }

    private boolean detectJSF22()
    {
        String version = FacesContext.class.getPackage()
                .getImplementationVersion();

        if (version != null) {
            return version.startsWith("2.2");
        }

        try {
            Class.forName("javax.faces.flow.Flow");
            return true;
        } catch (ClassNotFoundException ex) {
        }
        return false;
    }
}
