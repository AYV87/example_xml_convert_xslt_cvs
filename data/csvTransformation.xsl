<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    <xsl:variable name="separator" select="','"/>
    <!--<xsl:variable name="separator" select="'&#59;'"/>  точка-запятая -->
    <xsl:variable name="newline" select="'&#10;'"/>
    <xsl:template match="/">
        <xsl:text>id_art,name,code,username,guid,</xsl:text>
        <xsl:value-of select="$newline"/>
        <xsl:for-each select="//article">
            <xsl:value-of select="id_art"/>
            <xsl:value-of select="$separator"/>
            <xsl:value-of select="name"/>
            <xsl:value-of select="$separator"/>
            <xsl:value-of select="code"/>
            <xsl:value-of select="$separator"/>
            <xsl:value-of select="username"/>
            <xsl:value-of select="$separator"/>
            <xsl:value-of select="guid"/>
            <xsl:value-of select="$separator"/>
            <xsl:value-of select="$newline"/>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
