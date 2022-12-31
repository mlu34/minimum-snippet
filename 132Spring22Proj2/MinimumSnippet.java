import java.util.List;
import java.util.ArrayList;

/**
 * A MinimumSnippet is the shortest subsequence in a document that contains all the search terms. 
 * 
 * Given a document (a sequence of words) and set of search terms, this class will find the shortest subsequence in 
 * the document that contains all the search terms. If there are multiple subsequences that have the same minimal 
 * length, only one will be returned.
 */
public class MinimumSnippet {

	public Iterable<String> document; // the text of words we want to find the terms in
	public List<String> terms; // the list of words we want to find
	public ArrayList<String> docArr; // "document" variable in type ArrayList
	public ArrayList<String> minimumSnippet; // contains the shortest snippet in the document
	
	int startIndexSnippet; // the index in the document of the first snippet
	int endIndexSnippet; // the index in the document of the last snippet

	/**
	 * This constructor finds the shortest subsequence in the document that contains all the terms in the list.
	 * 
	 * It takes in the parameters document and terms. The terms will be unique (no term will be repeated). 
	 * If "terms" is empty, it will throw an IllegalArgumentException.
	 */
	public MinimumSnippet(Iterable<String> document, List<String> terms) {

		this.document = document;
		this.terms = terms;
		docArr = toArrayList(document);
		
		minimumSnippet = new ArrayList<>(docArr); // sets minimumSnippet to

		// throws an IllegalArgumentException if "terms" is empty
		if(terms.isEmpty()) {
			throw new IllegalArgumentException();
		}

		// checks if all the terms are found inside document
		if(foundAllTerms()) {
			boolean hasAllTerms = true; // used to check if there is more than one snippet found in the document
			int pos = 0; // dictates the position docArr will start from if there is more than one snippet found

			ArrayList<String> tempDocArr = new ArrayList<>(docArr);

			while(hasAllTerms){
				ArrayList<String> tempTerms = new ArrayList<>(terms);
				List<String> tempSnippet = new ArrayList<>();
				ArrayList<String> termsFound = new ArrayList<>();

				for(int docIndex = pos; docIndex < docArr.size(); docIndex++) {
					String currWord = docArr.get(docIndex);

					for(String currTerm: tempTerms) {
						if(currWord.equals(currTerm) && !termsFound.contains(currTerm)) {
							// this if statement only runs once, which adds the first term found into the snippet
							if(tempSnippet.isEmpty()) {
								startIndexSnippet = docIndex;
								pos = docIndex + 1;
								tempDocArr.remove(currTerm);
							}
							tempSnippet.add(currWord);
							termsFound.add(currTerm);
							
							if(tempSnippet.containsAll(terms)) {
								endIndexSnippet = docIndex;
								if(tempSnippet.size() < minimumSnippet.size()) {
									minimumSnippet = (ArrayList<String>) tempSnippet;
								}
								break;
							}		
							break;
							
						} else if(!tempSnippet.isEmpty() && !currWord.equals(currTerm) 
								&& tempTerms.indexOf(currTerm) + 1 == tempTerms.size()) {
							tempSnippet.add(currWord);
							
						} else {
							continue;
						}
					}
					if(tempSnippet.containsAll(terms)) {
						break;
					}
				}
				if(!tempDocArr.containsAll(terms)) {
					hasAllTerms = false;
				}
			}
		}
	}

	/* This method copies the words in the document to an ArrayList and returns it. */
	private ArrayList<String> toArrayList(Iterable<String> document){
		ArrayList<String> documentList = new ArrayList<>();
		for(String word: document) {
			documentList.add(word);
		}
		return documentList;
	}

	/**
	 * This method checks if all terms were found in the document.
	 * 
	 * It will return true if all terms were found, and false otherwise. If all terms were not found,
	 * then none of the other methods should be called. 
	 */
	public boolean foundAllTerms() {
		if(docArr.containsAll(terms)) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns the starting position of the snippet.
	 * 
	 * It returns the index in the document of the first element of the smallest snippet. 
	 */
	public int getStartingPos() {
		return startIndexSnippet;
	}

	/**
	 * This method returns the ending position of the snippet.
	 * 
	 * It returns the index in the document of the last element of the smallest snippet. 
	 */
	public int getEndingPos() {
		return endIndexSnippet;
	}

	/* This method returns the total number of elements contained in the smallest snippet. */
	public int getLength() {
		return minimumSnippet.size();
	}

	/**
	 * This method returns the position of the search term in the original document.
	 * The index parameter represents the index of the term in the original list of terms. 
	 * 
	 * For example, if index is 0 then the method will return the position (in the document) of the first search term. 
	 * If the index is 1, then the method will return the position (in the document) of the second search term.
	 */
	public int getPos(int index) {
		String termToFind = terms.get(index); // the term we want to find the position of
		int posInSnippet = 0; // stores the index of termToFind inside the snippet

		// used to find the index of termToFind by checking every word in the snippet
		for(String word: minimumSnippet) {
			if(word.equals(termToFind)) {
				break;
			}	
			posInSnippet++;
		}
		/* returns the value of the index where the snippet starts inside the document and 
		 * the index of the term inside the snippet */
		return posInSnippet + startIndexSnippet; 
	}
}
