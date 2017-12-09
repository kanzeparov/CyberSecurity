package ru.mipt.cybersecurity.codec;

/*
002 * Licensed to the Apache Software Foundation (ASF) under one
003 * or more contributor license agreements.  See the NOTICE file
004 * distributed with this work for additional information
005 * regarding copyright ownership.  The ASF licenses this file
006 * to you under the Apache License, Version 2.0 (the
007 * "License"); you may not use this file except in compliance
008 * with the License.  You may obtain a copy of the License at
009 *
010 *     http://www.apache.org/licenses/LICENSE-2.0
011 *
012 * Unless required by applicable law or agreed to in writing,
013 * software distributed under the License is distributed on an
014 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
015 * KIND, either express or implied.  See the License for the
016 * specific language governing permissions and limitations
017 * under the License.
018 */


        import ru.mipt.cybersecurity.util.SimpleByteSource;

        import java.io.File;
        import java.io.InputStream;

        /**
 025 * A {@code ByteSource} wraps a byte array and provides additional encoding operations.  Most users will find the
 026 * {@link Util} inner class sufficient to construct ByteSource instances.
 027 *
 028 * @since 1.0
 029 */
        public interface ByteSource {

            /**
 033     * Returns the wrapped byte array.
 034     *
 035     * @return the wrapped byte array.
 036     */
            byte[] getBytes();

            /**
 040     * Returns the <a href="http://en.wikipedia.org/wiki/Hexadecimal">Hex</a>-formatted String representation of the
 041     * underlying wrapped byte array.
 042     *
 043     * @return the <a href="http://en.wikipedia.org/wiki/Hexadecimal">Hex</a>-formatted String representation of the
 044     *         underlying wrapped byte array.
 045     */
            String toHex();

            /**
 049     * Returns the <a href="http://en.wikipedia.org/wiki/Base64">Base 64</a>-formatted String representation of the
 050     * underlying wrapped byte array.
 051     *
 052     * @return the <a href="http://en.wikipedia.org/wiki/Base64">Base 64</a>-formatted String representation of the
 053     *         underlying wrapped byte array.
 054     */
            String toBase64();

            /**
 058     * Returns {@code true} if the underlying wrapped byte array is null or empty (zero length), {@code false}
 059     * otherwise.
 060     *
 061     * @return {@code true} if the underlying wrapped byte array is null or empty (zero length), {@code false}
 062     *         otherwise.
 063     * @since 1.2
 064     */
            boolean isEmpty();

            /**
 068     * Utility class that can construct ByteSource instances.  This is slightly nicer than needing to know the
 069     * {@code ByteSource} implementation class to use.
 070     *
 071     * @since 1.2
 072     */
            public static final class Util {

                /**
 076         * Returns a new {@code ByteSource} instance representing the specified byte array.
 077         *
 078         * @param bytes the bytes to represent as a {@code ByteSource} instance.
 079         * @return a new {@code ByteSource} instance representing the specified byte array.
 080         */
                public static ByteSource bytes(byte[] bytes) {
                        return (ByteSource) new SimpleByteSource(bytes);
                    }

                /**
 086         * Returns a new {@code ByteSource} instance representing the specified character array's bytes.  The byte
 087         * array is obtained assuming {@code UTF-8} encoding.
 088         *
 089         * @param chars the character array to represent as a {@code ByteSource} instance.
 090         * @return a new {@code ByteSource} instance representing the specified character array's bytes.
 091         */
                public static ByteSource bytes(char[] chars) {
                        return (ByteSource) new SimpleByteSource(chars);
                    }

                /**
 097         * Returns a new {@code ByteSource} instance representing the specified string's bytes.  The byte
 098         * array is obtained assuming {@code UTF-8} encoding.
 099         *
 100         * @param string the string to represent as a {@code ByteSource} instance.
 101         * @return a new {@code ByteSource} instance representing the specified string's bytes.
 102         */
                public static ByteSource bytes(String string) {
                        return (ByteSource) new SimpleByteSource(string);
                    }

                /**
 108         * Returns a new {@code ByteSource} instance representing the specified ByteSource.
 109         *
 110         * @param source the ByteSource to represent as a new {@code ByteSource} instance.
 111         * @return a new {@code ByteSource} instance representing the specified ByteSource.
 112         */
                public static ByteSource bytes(ByteSource source) {
                        return (ByteSource) new SimpleByteSource((File) source);
                    }

                /**
 118         * Returns a new {@code ByteSource} instance representing the specified File's bytes.
 119         *
 120         * @param file the file to represent as a {@code ByteSource} instance.
 121         * @return a new {@code ByteSource} instance representing the specified File's bytes.
 122         */
                public static ByteSource bytes(File file) {
                        return (ByteSource) new SimpleByteSource(file);
                    }

                /**
 128         * Returns a new {@code ByteSource} instance representing the specified InputStream's bytes.
 129         *
 130         * @param stream the InputStream to represent as a {@code ByteSource} instance.
 131         * @return a new {@code ByteSource} instance representing the specified InputStream's bytes.
 132         */
                public static ByteSource bytes(InputStream stream) {
                        return (ByteSource) new SimpleByteSource(stream);
                    }

                /**
 138         * Returns {@code true} if the specified object can be easily represented as a {@code ByteSource} using
 139         * the {@link ByteSource.Util}'s default heuristics, {@code false} otherwise.
 140         * <p/>
 141         * This implementation merely returns {@link SimpleByteSource}.{@link SimpleByteSource#isCompatible(Object) isCompatible(source)}.
 142         *
 143         * @param source the object to test to see if it can be easily converted to ByteSource instances using default
 144         *               heuristics.
 145         * @return {@code true} if the specified object can be easily represented as a {@code ByteSource} using
 146         *         the {@link ByteSource.Util}'s default heuristics, {@code false} otherwise.
 147         */
                public static boolean isCompatible(Object source) {
                        return SimpleByteSource.isCompatible(source);
                    }

                /**
 153         * Returns a {@code ByteSource} instance representing the specified byte source argument.  If the argument
 154         * <em>cannot</em> be easily converted to bytes (as is indicated by the {@link #isCompatible(Object)} JavaDoc),
 155         * this method will throw an {@link IllegalArgumentException}.
 156         *
 157         * @param source the byte-backed instance that should be represented as a {@code ByteSource} instance.
 158         * @return a {@code ByteSource} instance representing the specified byte source argument.
 159         * @throws IllegalArgumentException if the argument <em>cannot</em> be easily converted to bytes
 160         *                                  (as indicated by the {@link #isCompatible(Object)} JavaDoc)
 161         */
                public static ByteSource bytes(Object source) throws IllegalArgumentException {
                        if (source == null) {
                                return null;
                            }
                        if (!isCompatible(source)) {
                                String msg = "Unable to heuristically acquire bytes for object of type [" +
                                                source.getClass().getName() + "].  If this type is indeed a byte-backed data type, you might " +
                                                "want to write your own ByteSource implementation to extract its bytes explicitly.";
                                throw new IllegalArgumentException(msg);
                            }
                        if (source instanceof byte[]) {
                                return bytes((byte[]) source);
                            } else if (source instanceof ByteSource) {
                                return (ByteSource) source;
                            } else if (source instanceof char[]) {
                                return bytes((char[]) source);
                            } else if (source instanceof String) {
                                return bytes((String) source);
                            } else if (source instanceof File) {
                                return bytes((File) source);
                            } else if (source instanceof InputStream) {
                                return bytes((InputStream) source);
                            } else {
                                throw new IllegalStateException("Encountered unexpected byte source.  This is a bug - please notify " +
                                                "the Shiro developer list asap (the isCompatible implementation does not reflect this " +
                                                "method's implementation).");
                            }
                    }
    }
}



























































