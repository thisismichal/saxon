<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

        <xsl:output method="html" indent="yes" version="5.0" encoding="UTF-8"/>



        <xsl:template match="sample">
        <html>
        	<head>
                <link rel="stylesheet" type="text/css" href="style.css"/>
        	</head>
        	<body class="Table">
                <table border="1" cellpadding="10">
                    <tr>
                        <th class="Section" colspan="3">Using the format-date function</th>
                    </tr>
                    <tr>
                        <th>Code</th>
                        <th>Czech</th>
                        <th>English</th>
                    </tr>
                    <tr>
                        <td>format-date(xs:date('2015-10-11'), '[D]. [MNn] [Y]', 'cs', 'AD', '')</td>
                        <td><xsl:value-of select="format-date(xs:date('2015-10-11'), '[D]. [MNn] [Y]', 'cs', 'AD', '')"/></td>
                        <td><xsl:value-of select="format-date(xs:date('2015-10-11'), '[D]. [MNn] [Y]', 'en', 'AD', '')"/></td>
                    </tr>
                    <tr>
                        <td>format-date(current-date(), '[dwo] [MNn] [Y], [E]', 'cs', 'AD', '')</td>
                        <td><xsl:value-of select="format-date(current-date(), '[dwo] [MNn] [Y], [E]', 'cs', 'AD', '')"/></td>
                        <td><xsl:value-of select="format-date(current-date(), '[dwo] [MNn] [Y], [E]', 'en', 'AD', '')"/></td>
                    </tr>
                    <tr>
                        <td>format-dateTime(current-dateTime(), '[h]:[m01] [Pn], [FNn]', 'cs', 'AD', '')</td>
                        <td><xsl:value-of select="format-dateTime(current-dateTime(), '[h]:[m01] [Pn], [FNn]', 'cs', 'AD', '')"/></td>
                        <td><xsl:value-of select="format-dateTime(current-dateTime(), '[h].[m01] [Pn], [FNn]', 'en', 'AD', '')"/></td>
                    </tr>

                    <tr>
                        <th class="Section" colspan="3">Converting cardinal numbers to words</th>
                    </tr>
                    <tr>
                        <th>Code</th>
                        <th>Czech</th>
                        <th>English</th>
                    </tr>
                    <tr>
                        <td><![CDATA[<xsl:number value='5' format='Ww' lang='cs'/>]]></td>
                        <td><xsl:number value='5' format='Ww' lang='cs'/></td>
                        <td><xsl:number value='5' format='Ww' lang='en'/></td>
                    </tr>
                    <tr>
                        <td><![CDATA[<xsl:number value='364' format='w' lang='cs'/>]]></td>
                        <td><xsl:number value='364' format='w' lang='cs'/></td>
                        <td><xsl:number value='364' format='w' lang='en'/></td>
                    </tr>
                    <tr>
                        <td><![CDATA[<xsl:number value='78675' format='w' lang='cs'/>]]></td>
                        <td><xsl:number value='78675' format='w' lang='cs'/></td>
                        <td><xsl:number value='78675' format='w' lang='en'/></td>
                    </tr>
                    <tr>
                        <td><![CDATA[<xsl:number value='138229221' format='w' lang='cs'/>]]></td>
                        <td><xsl:number value='138229221' format='w' lang='cs'/></td>
                        <td><xsl:number value='138229221' format='w' lang='en'/></td>
                    </tr>

                    <tr>
                        <th class="Section" colspan="3">Converting ordinal numbers to words (with the appropriate ordinal parameter)</th>
                    </tr>
                    <tr>
                        <th>Code</th>
                        <th>Czech</th>
                        <th>English</th>
                    </tr>
                    <tr>
                        <td><![CDATA[<xsl:number value='5' format='w' ordinal="-m" lang='cs'/>]]></td>
                        <td><xsl:number value='5' format='w' ordinal="-m" lang='cs'/></td>
                        <td><xsl:number value='5' format='w' ordinal="-m" lang='en'/></td>
                    </tr>
                    <tr>
                        <td><![CDATA[<xsl:number value='28' format='w' ordinal="-ž" lang='cs'/>]]></td>
                        <td><xsl:number value='28' format='w' ordinal="-ž" lang='cs'/></td>
                        <td><xsl:number value='28' format='w' ordinal="-ž" lang='en'/></td>
                    </tr>
                    <tr>
                        <td><![CDATA[<xsl:number value='92' format='w' ordinal="-s" lang='cs'/>]]></td>
                        <td><xsl:number value='92' format='w' ordinal="-s" lang='cs'/></td>
                        <td><xsl:number value='92' format='w' ordinal="-s" lang='en'/></td>
                    </tr>
                </table>
                <table class="Months" border="1" cellpadding="10">
                    <tr>
                        <th class="Section" colspan="3">Months</th>
                    </tr>
                    <tr>
                        <th>#</th>
                        <th>English</th>
                        <th>Czech</th>
                    </tr>
                    <xsl:for-each select="for $i in 1 to 12 return $i">
                      <tr>
                        <xsl:variable name="monthNum" as="xs:string" select="concat('2015-', format-number(., '00'), '-11')"/>
                        <td><xsl:value-of select='.'/></td>
                        <td><xsl:value-of select="format-date(xs:date($monthNum), '[MNn]', 'en', 'AD', '')"/></td>
                        <td><xsl:value-of select="format-date(xs:date($monthNum), '[MNn]', 'cs', 'AD', '')"/></td>
                      </tr>
                    </xsl:for-each>
                </table>
                <table class="Numbers" border="1" cellpadding="10">
                    <tr>
                        <th class="Section" colspan="5">Numbers</th>
                    </tr>
                    <tr>
                        <th>#</th>
                        <th>Cardinal</th>
                        <th>Ordinal (-m)</th>
                        <th>Ordinal (-ž)</th>
                        <th>Ordinal (-s)</th>
                    </tr>
                      <xsl:for-each select="for $i in 1 to 1000 return $i">
                    <tr>
                        <xsl:variable name="num" as="xs:decimal" select="."/>
                        <td><xsl:value-of select='$num'/></td>
                        <td><xsl:number value='$num' format='w' lang='cs'/></td>
                        <td><xsl:number value='$num' format='w' ordinal="-m" lang='cs'/></td>
                        <td><xsl:number value='$num' format='w' ordinal="-ž" lang='cs'/></td>
                        <td><xsl:number value='$num' format='w' ordinal="-s" lang='cs'/></td>
                      </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
        </xsl:template>

</xsl:stylesheet>
