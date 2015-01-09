<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    
        <xsl:output method="html" indent="yes" version="5.0" encoding="UTF-8"/>
        
		
        
        <xsl:template match="/">
        <html>
        	<head>
        	</head>
        	<body>
	            <xsl:value-of select="format-date(current-date(), '[D]. [M]. [Y]')"/>
	            <xsl:text>&#10;</xsl:text>
	            <xsl:value-of select="format-date(current-date(), '[D,2]/[M,2]/[Y,4]')"/>
	            <xsl:text>&#10;</xsl:text>
	            <xsl:value-of select="format-date(current-date(), '[F], [MNn] [D1o], [Y]')"/>
	            <xsl:text>&#10;</xsl:text>
	            <xsl:value-of select="format-date(xs:date('2009-12-04'), '[DWwo] [MNn] [Y]', 'cs', 'AD', '')"/>
	            <xsl:text>&#10;</xsl:text>
	            <xsl:value-of select="format-date(current-date(), '[dWwo] [MNn] [Y] [E]', 'cs', 'AD', '')"/>
	            <xsl:text>&#10;</xsl:text>
	            <xsl:value-of select="current-time()"/>
	            <xsl:text>&#10;</xsl:text>
	            <xsl:value-of select="format-time(current-time(), '[H]:[m]:[s]')"/>
	            <xsl:text>&#10;</xsl:text>
	            <xsl:value-of select="format-time(current-time(), '[h]:[m]:[s] [P]')"/>
            </body>
        </html>
        </xsl:template>
        
</xsl:stylesheet>