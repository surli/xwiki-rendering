/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.docbook;

import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.parser.Parser;
import org.xwiki.rendering.renderer.BlockRenderer;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.test.jmock.AbstractComponentTestCase;

/**
 * Unit tests for {@link org.xwiki.rendering.internal.parser.docbook.DocBookParser}.
 *
 * @version $Id$
 * @since 4.1M1
 */
public class DocBookParserTest extends AbstractComponentTestCase
{
    @Ignore("Ignored till we fix the DocBook parser to make it pass!")
    @Test
    public void parseDocbookExample() throws Exception
    {
        Parser parser = getComponentManager().getInstance(Parser.class, "docbook/4.4");
        XDOM xdom = parser.parse(new InputStreamReader(getClass().getResourceAsStream("/docbook/example.xml")));

        BlockRenderer renderer = getComponentManager().getInstance(BlockRenderer.class, "event/1.0");
        DefaultWikiPrinter printer = new DefaultWikiPrinter();
        renderer.render(xdom, printer);

        // Read expected content and remove license header for comparison.
        String expected = IOUtils.toString(getClass().getResourceAsStream("/docbook/expected.txt"));
        Pattern pattern = Pattern.compile("====.*====\n\n", Pattern.DOTALL);
        expected = pattern.matcher(expected).replaceFirst("");

        Assert.assertEquals(expected, printer.toString());
    }
}
