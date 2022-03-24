## CARTRAWLER CODE ASSESSMENT

Starting from the provided source, below are the changes introduced.

* The original cars were saved into a CSV file which is then loaded by using the apache-commons-csv  library (introduced 
  in the pom) - loading from an external source introduces flexibility, improves testability. statics aren't great.
* The list of cars was loaded, as the original code, into a Set, although a list would be a more natural container. Deduping 
  on a list is a bit more involved, thus for simplicity the Set was kept as it's a structure that provide the dedupe
  functionality out of the box. Collection were used as input/output, as they are a better abstraction and would allow
  changing Set to List with no breaking of the downstream processing.
* To perform check for duplicates the `CarResult` implements an `equals` and `hashCode` method that uses only the field
  required for the deduplication as required (i.e. the price is not used in both methods); `equals` and `hashCode` were
  implemented using helper methods from Apache `commons-lang3`. Although this may not implement the "natural" equality,
  those, together with loading data into a Set, allowed "for free" the resulting deduplication of the input dataset.
* A class takes care of the sorting according to spec, as per SOLID pattern
