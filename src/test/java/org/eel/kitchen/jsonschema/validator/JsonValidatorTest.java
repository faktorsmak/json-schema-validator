/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.eel.kitchen.jsonschema.main.JsonSchemaException;
import org.eel.kitchen.jsonschema.main.JsonSchemaFactory;
import org.eel.kitchen.jsonschema.main.SchemaContainer;
import org.eel.kitchen.jsonschema.main.SchemaNode;
import org.eel.kitchen.jsonschema.main.ValidationContext;
import org.eel.kitchen.jsonschema.main.ValidationReport;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public final class JsonValidatorTest
{
    private static final JsonNodeFactory factory = JsonNodeFactory.instance;
    private static final JsonSchemaFactory schemaFactory
        = new JsonSchemaFactory.Builder().build();

    private SchemaContainer container;
    private SchemaNode schemaNode;
    private ValidationContext context;
    private ValidationReport report;
    private JsonValidator validator;

    @BeforeMethod
    public void setupContext()
    {
        report = new ValidationReport();
        context = new ValidationContext(schemaFactory);
    }

    @Test
    public void refFailureShouldStopAtFirstStep()
        throws JsonSchemaException
    {
        final JsonNode node = factory.objectNode().put("$ref", "#");

        container = schemaFactory.registerSchema(node);
        schemaNode = new SchemaNode(container, node);
        context.setContainer(container);
        validator = new RefResolverJsonValidator(schemaFactory, schemaNode);

        assertFalse(validator.validate(context, report, factory.nullNode()));
        assertFalse(report.isSuccess());
    }
}