package com.avrisnox.util.doc_storage.interfaces;

/**ValidateAndRefactor
 * A simple interface that enforces both Validator and Refactor are implemented.
 * This allows for a slightly simpler function call in any event where *both* are desired.
 */
public interface ValidateAndRefactor extends Validator, Refactor { }
