package finalExtractors;

import java.util.Set;

import javatools.datatypes.FinalSet;
import basics.Fact;
import basics.RDFS;
import basics.Theme;
import extractors.CategoryExtractor;
import extractors.DisambiguationPageExtractor;
import extractors.HardExtractor;
import extractors.InfoboxExtractor;
import extractors.PersonNameExtractor;
import extractors.WordnetExtractor;

/**
 * YAGO2s - LabelExtractor
 * 
 * Deduplicates all label facts (except for the multilingual ones). This extractor is different from FactExtractor so that it can run in parallel. 
 * 
 * @author Fabian M. Suchanek
 * 
 */
public class LabelExtractor extends Deduplicator {

  @Override
  public Set<Theme> input() {
    return new FinalSet<>(CategoryExtractor.CATEGORYFACTS, DisambiguationPageExtractor.DISAMBIGUATIONMEANSFACTS,         
        HardExtractor.HARDWIREDFACTS, 
        InfoboxExtractor.INFOBOXFACTS,
        PersonNameExtractor.PERSONNAMES,
          WordnetExtractor.WORDNETWORDS,
        WordnetExtractor.WORDNETGLOSSES);
  }

  /** Relations that we care for*/
  public static Set<String> relations = new FinalSet<>(RDFS.label, "skos:prefLabel", "<isPreferredMeaningOf>", "<hasGivenName>",
      "<hasFamilyName>", "<hasGloss>");

  /** All facts of YAGO */
  public static final Theme YAGOLABELS = new Theme("yagoLabels", "All labels of YAGO");

  @Override
  public Theme myOutput() {   
    return YAGOLABELS;
  }

  @Override
  public boolean isMyRelation(Fact fact) {    
    return relations.contains(fact.getRelation());
  }
   
 }
