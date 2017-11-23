package ru.mipt.cybersecurity.util;

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

/**
 * 022 * Root exception for all Shiro runtime exceptions.  This class is used as the root instead
 * 023 * of {@link java.lang.SecurityException} to remove the potential for conflicts;  many other
 * 024 * frameworks and products (such as J2EE containers) perform special operations when
 * 025 * encountering {@link java.lang.SecurityException}.
 * 026 *
 * 027 * @since 0.1
 * 028
 */
public class ShiroException extends RuntimeException {

    /**
     * 032     * Creates a new ShiroException.
     * 033
     */
    public ShiroException() {
        super();
    }

    /**
     * 039     * Constructs a new ShiroException.
     * 040     *
     * 041     * @param message the reason for the exception
     * 042
     */
    public ShiroException(String message) {
        super(message);
    }

    /**
     * 048     * Constructs a new ShiroException.
     * 049     *
     * 050     * @param cause the underlying Throwable that caused this exception to be thrown.
     * 051
     */
    public ShiroException(Throwable cause) {
        super(cause);
    }

    /**
     * 057     * Constructs a new ShiroException.
     * 058     *
     * 059     * @param message the reason for the exception
     * 060     * @param cause   the underlying Throwable that caused this exception to be thrown.
     * 061
     */
    public ShiroException(String message, Throwable cause) {
        super(message, cause);
    }

}








