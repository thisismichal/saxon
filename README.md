#SaxonCS#


----------


##Using the Czech translation in XSLT/XML##
The only thing that needs to be changed in order for the translation to be used is to add/change the lang parameter **lang='cs'** when formatting the numbers and dates with functions such as format-date() or simply formating the xsl:number element.
```xml
<xsl:number value='364' format='w' lang='cs'/>
format-date(xs:date('2015-10-11'), '[D]. [MNn] [Y]', 'cs', 'AD', '')
```
The syntax doesn't change except for one thing mentioned below. The translations contains everything regarding the usual use of dates.

One thing to be aware of when trying to convert numbers to ordinal words is that supplying different ordinal parameters will yield different suffixes as expected in the Czech language.
```xml
 <xsl:number value='5' format='w' ordinal="XXX" lang='cs'/>
```
|  XXX |  result |
|------|---------|
| -m   |  patý   |
| -ž   |  pátá   |
| -s   |  páté   |


----------


##How to use this code##

###Using the jar from command line###
This extension delegates all arguments to the original library. This means that the use via command line is exactly same as when using the saxon9he.jar itself.
```
  java -jar SaxonHE_cs.jar -xsl:XXX -s:XXX -o:XXX
```
These parameters contain their respective **paths** to:

 - **-xsl:** the xsl stylesheet to be used in the transformation
 - **-s:** the source xml file
 - **-o** the output file


------------------
### Saxon Java API ###
In order to use the Numberer_cs class without the supplied wrapper you need to
take couple simple steps. These steps can be easily observed by just browsing the
```Saxon_cs``` and ```Transform_cs``` classes.

1. You need to create a new instance of the ```net.sf.saxon.Configuration```.
2. This instance contains a method ```setLocalizerFactory(LocalizerFactory f)```.
3. Register your own ```LocalizerFactory``` with this method.
   Your LocalizerFactory instance needs to override the ```getNumberer(String language, String country)```
method and return an instance of ```Numberer_cs``` when the languages parameter is equal
to 'cs'.
4. Now that the ```net.sf.saxon.Configuration``` instance has the ```LocalizerFactory``` with
   you need to extend the ```net.sf.saxon.Transform``` class and override the abstract method
   like this:
   ```java
   @Override
   public void initializeConfiguration(Configuration config) {
     this.processor = new Processor(config);
     }
   ```

5. Instantiate your ```Transform``` sublcass and supply the ```Configuration``` instance
   to the overriden method.

6. This ```Transform``` instance is everything you need for the transformation.
   You can now call the ```doTransform(args, "")``` on it with the arguments you would normally
   supply via command line.


----------


###Ant build script###
Running the default ant target produces the build directory which contains the jars directory with the produced jar and copied saxon9he.jar (the produced jar expects the saxon9he.jar to be in the same directory) and also a sample directory with the result of sample XSLT transformation. This results presents the basic translations enabled by the Numberer_cs.java.

**You need to mkdir the lib folder and supply the saxon9he.jar**



The Ant script expects following file structure:
```
> src
   | > saxoncs
       | Numberer_cs.java
       | SaxonCS.java
       | Transform_cs.java
> resources
   | data.xsl
   | src.xml
   | style.css
> lib
   | saxon9he.jar
build.xml
```
