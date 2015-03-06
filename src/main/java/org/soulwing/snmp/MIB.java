/*
 * File created on Nov 30, 2012 
 *
 * Copyright (c) 2013 Carl Harris, Jr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.soulwing.snmp;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * A façade for a MIB implementation.
 * <p>
 * This interface defines methods for the essential aspects of a MIB
 * commonly used in writing SNMP management applications.
 *
 * @author Carl Harris
 */
public interface MIB {

  /**
   * Converts an object name to the corresponding object identifier.
   * <P>
   * If the name is suffixed by an instance identifier, the instance identifier
   * is appended to the returned object identifier.  For example, given the
   * name "sysName.0", this method returns 1.3.6.1.2.1.1.5.0, the OID for
   * the singleton instance of the "sysName" object.
   * <p>
   * When searching the MIB for the given name, all currently loaded MIB
   * modules are searched, in the reverse of the order in which they were 
   * loaded. The specified name may be optionally prefixed by a MIB module 
   * name followed by the '!' character (e.g. "RFC1213-MIB!sysName.0") in 
   * order to constrain the search to a specific MIB module.
   * 
   * @param name name to convert
   * @return object identifier string
   * @throws NameNotFoundException if {@code name} cannot be resolved to
   *    an object identifier
   */
  String nameToOid(String name);

  /**
   * Converts an object identifier string to the corresponding 
   * instance-qualified object name.
   * <p>
   * This method is the logical inverse of {@link #nameToOid(String)}.  It
   * returns the object name, suffixed by the object instance identifier.
   * For example, given the 1.3.6.1.2.1.1.5.0 (the OID for the singleton 
   * instance of the "sysName" object), this method returns "sysName.0"
   * 
   * @param oid the object identifier to convert
   * @return object name.  If the identifier refers to an object instance, the
   *    returned name is suffixed by the instance identifier.  If the identifier
   *    does not correspond to a known name, the return value is {@code oid}.
   */
  String oidToInstanceName(String oid);
  
  /**
   * Converts an object identifier string to the corresponding MIB object name.
   * <p>
   * This method simply determines the MIB name associated with the given 
   * object identifier (ignoring any instance identifier).  For example,
   * given 1.3.6.1.2.1.1.5.0 (the OID for the singleton instance of the 
   * "sysName" object), this method returns "sysName"
   * 
   * @param oid the object identifier to convert
   * @return name that corresponds to the given OID.  If the identifier does
   *    not correspond to a known name, the return value is {@code oid}. 
   */
  String oidToObjectName(String oid);
  
  /**
   * Gets the SMI syntax indicator for the given object identifier.
   * @param oid OID of an OBJECT-TYPE (or a subordinate)
   * @return syntax indicator
   * @throws IllegalArgumentException if {@code oid} does not refer to 
   *    an an OBJECT-TYPE or subordinate
   */
  int syntaxForObject(String oid);
  
  /**
   * Gets a new {@link Formatter} appropriate for the object type represented
   * by the given object identifier
   * @param oid object identifier
   * @return formatter object (never {@code null})
   */
  Formatter newFormatter(String oid);
  
  /**
   * Gets a new {@link IndexExtractor} appropriate for the table column
   * object represented by the given object identifier.
   * @param oid identifier of a table column object
   * @return index extractor (never {@code null})
   * @throws IllegalArgumentException if the {@code oid} does not represent
   *    a (known) table column
   */
  IndexExtractor newIndexExtractor(String oid);

  /**
   * Loads a named MIB module.
   * <p>
   * @param moduleName name of the MIB module to load
   * @throws ModuleParseException if the specified MIB module cannot be parsed
   * @throws IOException
   */
  void load(String moduleName) throws ModuleParseException, IOException;

  /**
   * Loads a MIB module from given location.
   * @param location location of the MIB module
   * @throws ModuleParseException if the resource at {@code location} cannot
   *    be parsed as a MIB
   * @throws IOException
   */
  void load(URL location) throws ModuleParseException, IOException;
  
  /**
   * Loads a MIB module from given file.
   * @param file file to load
   * @throws ModuleParseException if the resource at {@code location} cannot
   *    be parsed as a MIB
   * @throws IOException
   */
  void load(File file) throws ModuleParseException, IOException;
  
}
