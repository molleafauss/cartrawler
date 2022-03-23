## CARTRAWLER CODE ASSESSMENT

Starting from the provided source, below are the changes introduced.

* The original cars were saved into a CSV file which is then loaded by using the apache-commons-csv  library (introduced 
  in the pom) - loading from an external source introduces flexibility, improves testability. statics aren't great.
* To perform check for duplicates the `CarResult` implements an `equals` and `hashCode` method that uses only the field
  required for the deduplication as required (i.e. the price is not used in both methods); `equals` and `hashCode` were
  implemented using helper methods from Apache `commons-lang3`
