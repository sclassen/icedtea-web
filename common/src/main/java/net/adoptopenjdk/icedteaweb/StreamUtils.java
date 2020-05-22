/* 
Copyright (C) 2013 Red Hat, Inc.

This file is part of IcedTea.

IcedTea is free software; you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, version 2.

IcedTea is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with
IcedTea; see the file COPYING. If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is making a
combined work based on this library. Thus, the terms and conditions of the GNU
General Public License cover the whole combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent modules, and
to copy and distribute the resulting executable under terms of your choice,
provided that you also meet, for each linked independent module, the terms and
conditions of the license of that module. An independent module is a module
which is not derived from or based on this library. If you modify this library,
you may extend this exception to your version of the library, but you are not
obligated to do so. If you do not wish to do so, delete this exception
statement from your version.
*/

package net.adoptopenjdk.icedteaweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StreamUtils {

    public static String readStreamAsString(final InputStream stream) throws IOException {
        return readStreamAsString(stream, false);
    }

    public static String readStreamAsString(final InputStream stream, final Charset encoding) throws IOException {
        return readStreamAsString(stream, false, encoding);
    }

    public static String readStreamAsString(InputStream stream, boolean includeEndOfLines) throws IOException {
        return readStreamAsString(stream, includeEndOfLines, UTF_8);
    }

    public static String readStreamAsString(final InputStream stream, final boolean includeEndOfLines, final Charset encoding) throws IOException {
        final InputStreamReader is = new InputStreamReader(stream, encoding);
        final StringBuilder sb = new StringBuilder();
        final BufferedReader br = new BufferedReader(is);
        while (true) {
            final String read = br.readLine();
            if (read == null) {
                break;
            }
            sb.append(read);
            if (includeEndOfLines) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public static <T> Stream<T> loadServiceAsStream(Class<T> serviceInterface) {
        return toStream(ServiceLoader.load(serviceInterface).iterator());
    }

    public static <T> Stream<T> toStream(Iterator<T> iterator) {
        return StreamSupport.stream(((Iterable<T>) (() -> iterator)).spliterator(), false);
    }
}
