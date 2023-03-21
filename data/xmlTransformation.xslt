<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="articles">
        <xsl:element name="articles">
            <xsl:for-each select="article">
                <xsl:element name="article">
                    <id_art><xsl:value-of select="@id_art"/></id_art>
                    <name><xsl:value-of select="@name"/></name>
                    <code><xsl:value-of select="@code"/></code>
                    <username><xsl:value-of select="@username"/></username>
                    <guid><xsl:value-of select="@guid"/></guid>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>


