package org.example



class JobController {

    static allowedMethods = [save: "POST"]

    def index() {
        params.max = 10
        [jobInstanceList: Job.list(params), jobInstanceTotal: Job.count()]
    }

    def create() {
    }

    def upload() {
        def file = request.getFile('file')
        if(file.empty) {
            flash.message = "File cannot be empty"
        } else {
            def jobInstance = new Job()
            jobInstance.filename = file.originalFilename
            jobInstance.fullPath = grailsApplication.config.uploadFolder + jobInstance.filename
           /* file.transferTo(new File(jobInstance.fullPath))
            jobInstance.save()*/
         /*   InputStream fileStream = file.getInputStream()        
            	    newClass job1 = new newClass(fileStream);
	            job1.writeExcel();*/
                    InputStream fileStream = file.getInputStream() 
             
        Job.mmm1(fileStream)
        }
        redirect (action:'index')
    }

    def download(long id) {
        Job jobInstance = Job.get(id)
        if ( jobInstance == null) {
            flash.message = "Job not found."
            redirect (action:'index')
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM")
            response.setHeader("Content-Disposition", "Attachment;Filename=\"${jobInstance.filename}\"")

            def file = new File(jobInstance.fullPath)
            def fileInputStream = new FileInputStream(file)
            def outputStream = response.getOutputStream()

            byte[] buffer = new byte[4096];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.flush()
            outputStream.close()
            fileInputStream.close()
        }
    }
 
   
    }

