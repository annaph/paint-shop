# Paint Shop
Solution for paint shop Programming Challenge.

## Description
Refer to Programming Challenge doc for details on required functionalities.
Also, see `com.gilt.challenge.PaintShop` scaladoc for detailed description of algorithm used to mix colors.

### Pre-requirements
Pre-requirements to successfully compile and run challenge solution are:
  - Linux/Windows
  - Oracle JDK 8

### Usage
For compiling, running unit tests and running the solution Lightbend Activator can be used. Activator launcher and executable scripts are included in the project on _bin/_ and _libexec/_ locations.
If on Linux machine, make sure Activator script has sufficent permissions to be run:
```sh
$ chmod 777 bin/activator
```
Alternatively, if SBT is installed on machine, it can be directly used instead.
Following subsections explain solution usage using Lightbend Activator.
##### Compiling
Linux:

```sh
$ bin/activator compile
```
Windows:
```bat
> bin\activator.bat compile
```

##### Run unit tests
Linux:
```sh
$ bin/activator test
```
Windows:
```bat
> bin\activator.bat test
```
##### Create executable jar file
Linux:
```sh
$ bin/activator package
```
Windows:
```bat
> bin\activator.bat package
```
Upon completion executable jar file _paint-shop_2.12-1.0.jar_ can be found in _target/scala-2.12_ folder.
##### Create Eclipse project files
Linux:
```sh
$ bin/activator eclipse
```
Windows:
```bat
> bin\activator.bat eclipse
```
Make sure Eclipse is using Scala 2.12.1 version and Eclipse project output folder is changed from _bin/_ to _classes/_!
If project output folder is not changed Eclipse could by accident delete Activator scripts located in _bin/_ folder. In that case re-download scripts from GitHub repository.

##### Run
Linux:
```sh
$ bin/activator "run <input_filepath>"
```
Windows:
```bat
> bin\activator.bat "run <input_filepath>"
```
If Scala 2.12.1 is installed on machine, following command can also be used for running on both Linux and Windows machines:
```sh
$ scala target/scala-2.12/paint-shop_2.12-1.0.jar <input_filepath>
```

### Examples
Project contains several examples of input files that cover different scenarios.

To run first scenario from Programming Challenge doc following command can be used:
```sh
$ bin/activator "run input1.txt"
```

To run scenario with no solution from Programming Challenge doc following command can be used:
```sh
$ bin/activator "run input2.txt"
```

To run a slightly richer scenario from Programming Challenge doc following command can be used:
```sh
$ bin/activator "run input4.txt"
```

To run yet an another scenario from Programming Challenge doc following command can be used:
```sh
$ bin/activator "run input3.txt"
```
