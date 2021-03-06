# Handling extended attributes on Linux

The following lines are specific to UNIX-like operating systems (no JNI code is used on Windows) :

Areca is mainly developed in Java, but it also uses some native code to handle files' attributes (extended attributes, ACL ...). This native code is contained in the "libarecafs.so" library.

If you want to compile this library, run the "build.xml" ant file or run the following commands:

```terminal
gcc -c -fPIC -lacl com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.c -o com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o
gcc -shared -lacl -Wl -o libarecafs.so  com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o
```

Once the compilation is completed, copy the library to `/usr/lib` or `/lib`.

Areca checks on startup that the `libarecafs.so` library is compatible with your system. If it is not the case, it automatically switches to a more basic implementation that uses standard commands (such as `chmod`, `chown`) and only handles basic attributes (no ACL or extended attributes).

To ensure that Areca will use `libarecafs.so` (and not the default implementation), edit the `/etc/areca/fwk.properties` file and set the `filesystem.accessor.impl` to `com.myJava.file.metadata.posix.jni.JNIMetaDataAccessor` (the default value is `com.myJava.file.metadata.posix.basic.DefaultMetaDataAccessor`)
On startup, Areca displays some information about the "filesystem accessor" which is used (you should see something like `Loading configured file metadata accessor : [com.myJava.file.metadata.posix.jni.JNIMetaDataAccessor]` in your log file or log tab)
