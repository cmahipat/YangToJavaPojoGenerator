<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:java="http://xml.apache.org/xslt/java"
    exclude-result-prefixes="java">

<xsl:template match="module">

<xsl:for-each select="grouping">

//Beginning
import com.google.gson.annotations.SerializedName;
import java.util.*;

<xsl:variable name="groupName" select="@name"/>
<xsl:variable name="baseName" select="uses/@name"/>
public class <xsl:value-of select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.capitalize($groupName)"/> <xsl:if test="$baseName"> extends <xsl:value-of select="$baseName"/> </xsl:if> {
<xsl:for-each select="leaf">
@SerializedName("<xsl:value-of select="@name"/>")
private <xsl:value-of select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.getJavaMappedDataType(type/@name)"/> _<xsl:value-of select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.convertCamelCase(@name)"/>;
</xsl:for-each>
<xsl:for-each select="leaf-list">
@SerializedName("<xsl:value-of select="@name"/>")
private List&lt;<xsl:value-of select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.getJavaMappedDataType(type/@name)"/>&gt; _<xsl:value-of select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.convertCamelCase(@name)"/>;
</xsl:for-each>

<xsl:for-each select="list">
@SerializedName("<xsl:value-of select="@name"/>")
private <xsl:value-of select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.getJavaMappedDataType(@name)"/> _<xsl:value-of select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.convertCamelCase(@name)"/>;
</xsl:for-each>

<xsl:for-each select="leaf">

<xsl:variable name="methodName" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.capitalize(@name)"/>
<xsl:variable name="param" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.convertCamelCase(@name)"/>
<xsl:variable name="dataType" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.getJavaMappedDataType(type/@name)"/>

public <xsl:value-of select="$dataType"/> get<xsl:value-of select="$methodName"/>() {
return this._<xsl:value-of select="$param"/>;
}

public void set<xsl:value-of select="$methodName"/>(<xsl:value-of select="$dataType"/> _<xsl:value-of select="$param"/>) {
 this._<xsl:value-of select="$param"/> = _<xsl:value-of select="$param"/>;
}
</xsl:for-each>

<xsl:for-each select="leaf-list">

<xsl:variable name="methodName" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.capitalize(@name)"/>
<xsl:variable name="param" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.convertCamelCase(@name)"/>
<xsl:variable name="dataType" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.getJavaMappedDataType(type/@name)"/>

public List&lt;<xsl:value-of select="$dataType"/>&gt; get<xsl:value-of select="$methodName"/>() {
return this._<xsl:value-of select="$param"/>;
}

public void set<xsl:value-of select="$methodName"/>(List&lt;<xsl:value-of select="$dataType"/>&gt; _<xsl:value-of select="$param"/>) {
 this._<xsl:value-of select="$param"/> = _<xsl:value-of select="$param"/>;
}
</xsl:for-each>

<xsl:for-each select="list">

<xsl:variable name="methodName" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.capitalize(@name)"/>
<xsl:variable name="param" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.convertCamelCase(@name)"/>
<xsl:variable name="dataType" select="java:org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util.getJavaMappedDataType(@name)"/>

public <xsl:value-of select="$dataType"/> get<xsl:value-of select="$methodName"/>() {
return this._<xsl:value-of select="$param"/>;
}

public void set<xsl:value-of select="$methodName"/>(<xsl:value-of select="$dataType"/> _<xsl:value-of select="$param"/>) {
 this._<xsl:value-of select="$param"/> = _<xsl:value-of select="$param"/>;
}
</xsl:for-each>

}
//Ending
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>