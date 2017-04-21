# Alexandrian Editors and the scholia of the Venetus A

Melody Wauke

Adviser: Professor Neel Smith, Classics Dept.

---

## Venetus A Manuscript (Marcianus Gr. Z. 454)

Folio 181 recto

![Folio 181 recto](https://github.com/mwauke/seniorThesis/blob/master/181r.jpg)

---

## The *Iliad*: Oral and Written Transmission

- Orally composed and transmitted, 2nd millennium B.C.E.
- Written editions emerge, mid-1st millennium B.C.E.
- Editors at the Library of Alexandria, 3rd and 2nd centuries B.C.E
- Venetus A, 10th century C.E.

---
## Sources for the Venetus A scholia

- Limited information on source material
- Manuscript cites four specific sources from 1st and 2nd centuries C.E.
- Alexandrian editors' editions no longer extant at time of manuscript construction

---

## Recovering Aristarchan Material

- Identify cluster of features which suggest source (Aristarchan, post-Aristarchan)
- create a model for identifying Aristarchan scholia

---

## The Data Set

- 18 books of scholia (~8000) from Homer Multitext's digital editions
  - includes personal name identifiers
- index of all critical signs 

---

## Significance of Critical Signs and corresponding scholia

- Critical Signs come from Aristarchus' editions
- Explanatory scholia (ὅτι...) transmitted with signs are also Aristarchan
- Only Zenodotus writes (ζηνόδοτος γράφει...)

---

## Topic Modeling

- Recurring patterns of co-occurring words
- Topic modeling scores range 0-1
---

## Significant Topics for Analysis

### Topic 6: Aristarchan Language 

![Topic 6](https://github.com/mwauke/seniorThesis/blob/master/Topic06.png)

---

### Topic 9: Language used when discussing Aristarchus

![Topic 9](https://github.com/mwauke/seniorThesis/blob/master/Topic09.png)

---

## Features for Analysis

### Aristarchan Features 
- Critical sign
- γράφει 
- Initial ὅτι 
- Higher topic 6 score

### Post-Aristarchan Features

- παρα Ζηνοδότῳ 
- Aristarchus' name
- Post-Aristarchan name
- Higher topic 9 score

---

## Features Across Three Zones

||Main|Intermarginal|Interior|
|---|:----|:------------|:-------|
|Total|3591|1217|818|
|Critical Sign|1562|482|345|
|γράφει|174|26|12|
|Initial ὅτι|885|234|183|
|παρα Ζηνοδότῳ|12|7|3|
|Aristarchus|272|236|105|
|Post Aristarchan Name|238|14|12|
|All Aristarchan Features|113|14|9|
|Topic 6 > .5|253|87|58|
|Topic 6 > .9|74|28|16|
|Topic 9 > .5|127|213|112|
|Topic 9 > .9|44|64|28|

---

## Manual Labeling on a random sample of 100 scholia

- Aristarchan
- Aristarchan paraphrase
- post-Aristarchan
- indeterminate

---

## Decision Tree using manually labeled training set

---

## Conclusions

- Analyze scholia as a collection of various features
- Main scholia contain the majority of Aristarchan and post-Aristarchan language
- Intermarginal and Interior scholia contain more Aristarchan paraphrases
- Zones are *not* the best feature for indicating language-type
- Working Aristarchan identifier
  - Possible to recover material directly from Aristarchus' editions (2nd century BCE)

---

## Acknowledgements 

- Professor Neel Smith, adviser
- Charlie Schufreider '17, collaborator
- Claude Hanley '18, research partner
- Professor Thomas Köntges, developer of ToPan
- Department of Classics
