# YangToJavaPojoGenerator

This project is intended to create POJO file(s) for a corresponding YANG file.

## Usage
As it is a Maven project, download and run maven install command to generate a jar file.
In order to create POJO files run the following command
**java -jar YangToJavaPojoGenerator-0.0.1-SNAPSHOT.jar input_dir output_dir**
YangToJavaPojoGenerator-0.0.1-SNAPSHOT.jar: Generated jar file
input_dir: Specify the directory in which the YANG file resides(along with the dependent YANG files)
output_dir: Specify the directory where the corresponding POJO file(s) must be created