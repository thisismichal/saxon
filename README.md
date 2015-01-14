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


### Java API ###
The java classes in package saxoncs can be used both independently and via the iterface in form on the SaxonCS.java class. The only job of SaxonCS.java is to register the appropriate configuration for the supplied saxon library a delegate the arguments back to the library.
When calling the SaxonCS in code simply use the supplied static transform method.
```java
SaxonCS.transform(args);
```


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