/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eel.kitchen.jsonschema.keyword;

import eel.kitchen.jsonschema.base.SimpleValidator;
import org.codehaus.jackson.JsonNode;

import java.math.BigDecimal;

public final class DivisibleByValidator
    extends SimpleValidator
{
    private final BigDecimal divisor;

    public DivisibleByValidator(final KeywordValidatorFactory ignored,
        final JsonNode schema, final JsonNode instance)
    {
        super(ignored, schema, instance);
        divisor = schema.get("divisibleBy").getDecimalValue();
    }

    @Override
    protected void validateInstance()
    {
        final BigDecimal number = instance.getDecimalValue();

        if (number.remainder(divisor).compareTo(BigDecimal.ZERO) == 0)
            return;

        report.addMessage("instance is not a multiple of divisibleBy");

    }
}