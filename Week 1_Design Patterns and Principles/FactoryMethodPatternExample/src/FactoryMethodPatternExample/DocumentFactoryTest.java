package FactoryMethodPatternExample;

public class DocumentFactoryTest {

    public static void main(String[] args) {
        // Create factories for different document types
        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        // Create documents using the factories
        Document wordDoc = wordFactory.createDocument();
        Document pdfDoc = pdfFactory.createDocument();
        Document excelDoc = excelFactory.createDocument();

        // Open the documents
        wordDoc.open();
        pdfDoc.open();
        excelDoc.open();
    }
}

