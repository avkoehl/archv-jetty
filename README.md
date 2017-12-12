# README #
This project is the API for archv. It relies on the code at https://bitbucket.org/cstahmer/archv. Whenever changes are made to the Server or any of the servlets, update the .java file. Then recompile. One change that needs to be made is every instance of String filePath= "//home//arthur//programs//work//archv//program.exe". This needs to be changed to the correct file path to the compiled archv.

To compile jetty:

```
#!bash

javac -d classes -cp jetty-all-uber.jar:jetty-servlets-9.3.10.M0.jar AllServlet.java

```

To start:

```
#!bash

java -cp classes:* org.eclipse.jetty.embedded.AllServlet

```

### Restful Api ###

Interact with Archv by submitting http GET requests handled by Jetty. Each valid GET request will recieve a response from Jetty in JSON form. Generally, this response will be a filepath, or a list of file paths. To submit a request in javascipt, do something like:


```
#!javascript

    function httpGetAsync(theUrl)
    {
      var xmlHttp = new XMLHttpRequest();
      xmlHttp.onreadystatechange = function()
      { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
          //Do something with xmlHttp
      }
      xmlHttp.open("GET", theUrl, true); // true for asynchronous 
      xmlHttp.send(null);
    }

```

The parameters are passing in through the url. Requesting ShowKeypoints would look like this:


```
#!bash

http://localhost:8888/show
http://localhost:8888/show/show?
http://localhost:8888/show/show?-param=value&-param2=value2...


```
In this case the parameters are as follows:

```
#!bash

-i = path/to/inputimage
-o = path/to/outputimage
-h = minhessian value
-oct = octave value
-l = octave layers value
-s = min size value
-r = min response value
```

An example url for showkeypoints could look like this:

```
#!bash
http://localhost:8080/show/show?-i=seed.jpg&-o=output.jpg&-h=3000&-oct=7&-l=7&-s=50&-r=100

```

### Enable CORS ###

Cross Origin Requests are enabled by the following code when the server is created:

```
#!java


//to enable CORS
FilterHolder holder = new FilterHolder (CrossOriginFilter.class);
holder.setInitParameter("allowedOrigins", "*");
holder.setInitParameter("allowedMethods", "GET,POST");
context.addFilter(holder, "/*", null);
```

### Outputs ###

Each request, if valid, has a response. Each response is a json object that contains the filename.


```
#!json

{"file": "path/to/file"}

```
ShowKeypoints and DrawMatches return the path to a jpeg image file. ProcessImages returns the path to the newly filled Keypoint directory (keep in mind, to run processImages you need to have created that directory manually (i.e mkdir Keypoint). ScanDatabase returns the path to a json file containing each file and the respective distance value.


### URLS ###

On server, replace localhost:8888 with ds.lib.ucdavis.edu:8888.

```
#!bash

http://localhost:8888/show/show?-param=value&-param2=value2...
http://localhost:8888/draw/draw?-param=value&-param2=value2...
http://localhost:8888/scan/scan?-param=value&-param2=value2...
http://localhost:8888/process/process?-param=value&-param2=value2...

```

### Parameters ###

Each request expects a series of inputs as well as SURF parameters, the SURF parameters can be passed in individually or through a parameter file like the one established in the archv repository. The parameters are as follows -

ShowKeypoints:
```
#!bash

-i = path/to/inputimage
-o = path/to/output.jpg

-h = minhessian value
-oct = octave value
-l = octave layers value
-s = min size value
-r = min response value
```

ProcessImages:
```
#!bash

-i = path/to/imageset
-o = path/to/keypointdirectory

-h = minhessian value
-oct = octave value
-l = octave layers value
-s = min size value
-r = min response value
```

ScanDatabase:

```
#!bash

-i = path/to/inputimage
-d = path/to/imageset
-k = path/to/keypointdirectory
-o = path/to/output.json

-h = minhessian value
-oct = octave value
-l = octave layers value
-s = min size value
-r = min response value
```

DrawMatches:

```
#!bash

-i1 = path/to/image1
-i2 = path/to/image2
-o = path/to/output.jpg

-h = minhessian value
-oct = octave value
-l = octave layers value
-s = min size value
-r = min response value
```
For all of the above, the last five parameters (h, oct, l, s, r) can replaced with -p = /path/to/paramfile.

### Debugging ###
In the process of debugging it may be useful to see what parameters are actually being read in and what their values are, this can be accomplished in a servlet function with the following java.


```
#!java

Enumeration pNames = request.getParameterNames();
while (pNames.hasMoreElements()) {
  String paramName = pNames.nextElement().toString();
  response.getWriter().println("Name: " + paramName + " Value: " + request.getParameter(paramName));

```

It may be useful to see what the programs are outputing, generally the archv programs have little output, but can be modified for debugging purposes. To display what the .exe executables are outputing into the terminal where jetty is running:


```
#!java

      InputStream iStream = process.getInputStream();
      BufferedReader BR = new BufferedReader (new InputStreamReader(iStream), 1);

      String line;
      while ((line = BR.readLine()) != null) {
        System.out.println(line);
      }

```
### Errors ###

If for whatever reason the code does not run succesfully, then the response will simply be the json:

```
#!json

{"error": "Syntax error in request, fix parameters"}

```

Most likely this is error happens because not all the parameters that are necessary were actually passed, or some were passed with incorrect values like result.tiff instead of result.jpg.
Some possible reasons could be the input image file not existing or the parameter file doesn't exist, and many more. I could write individual exceptions in cpp and java process could determine error I think, add to TODO.
Also, remember that the .exe can not create a directory so whenever you pass a directory in the path, make sure it exists, even for stuff like keypoint directories, you need to mkdir before runnning code. Opencv has almost no built in file checking and does not handle these sorts of errors.

### Paths ###


```
#!bash

Jetty: /var/www/vhost/ds.lib.ucdavis.edu/htdocs/archv/jetty/
Archv: /var/www/vhost/ds.lib.ucdavis.edu/htdocs/archv/archive-vision/

Flickr Param: /var/www/vhost/ds.lib.ucdavis.edu/htdocs/jetty/flickr_param
Flickr Param2: /var/www/vhost/ds.lib.ucdavis.edu/htdocs/jetty/flickr_param2
Ballad Param: /var/www/vhost/ds.lib.ucdavis.edu/htdocs/jetty/ballad_param

BL-Flickr Images: /var/www/vhost/ds.lib.ucdavis.edu/include/Bl-flickr/Smaller/
BL-Flickr Keypoints: /var/www/vhost/ds.lib.ucdavis.edu/include/Bl-flickr/Keypoints/
BL-Flickr Keypoints2: /var/www/vhost/ds.lib.ucdavis.edu/include/Bl-flickr/Keypoints2/

Ballad Images: /var/www/vhost/ds.lib.ucdavis.edu/include/Ballads/Images/
Ballad Keypoints: /var/www/vhost/ds.lib.ucdavis.edu/include/Ballads/Keypoints/

Result Directory: /var/www/vhost/ds.lib.ucdavis.edu/htdocs/archv/outputs/


```

All paths can be passed in relative to /var/www/vhost/ds.lib.ucdavis.edu/archv/jetty/
For example ballad images:

```
#!bash
../../../include/Ballads/Images

```




### Contact ###
Arthur Koehl
avkoehl@ucdavis.edu
