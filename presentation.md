## Alexandrian editors and the scholia of the Venetus A

Melody Wauke

Adviser: Professor Neel Smith, Classics Dept.

---

## Venetus A manuscript (Marcianus Gr. Z. 454)

Folio 181 recto

![Folio 181 recto](https://raw.githubusercontent.com/mwauke/seniorThesis/master/181r.jpg)

---

## The *Iliad*: oral and written transmission

- Orally composed and transmitted, 2nd millennium B.C.E.
- Written editions emerge, mid-1st millennium B.C.E.
- Editors at the Library of Alexandria, 3rd and 2nd centuries B.C.E
    - Zenodotus
    - Aristarchus
- Venetus A, 10th century C.E.

---

## Sources for the Venetus A scholia

- Limited information on source material
- Manuscript cites four specific sources from 1st and 2nd centuries C.E.
- Alexandrian editors' editions no longer extant at time of manuscript construction

---

## Recovering Aristarchan material

- Identify cluster of features which suggest source (Aristarchan, post-Aristarchan)
- Create a model for identifying Aristarchan scholia

---

## The data set

- 18 books of scholia (~8000) from Homer Multitext's digital editions
  - includes personal name identifiers
- index of all critical signs

---

## Suggestions of Aristarchan *language*



- Critical Signs come from Aristarchus' editions
- Explanatory scholia (ὅτι...) transmitted with signs are also Aristarchan
- Only Zenodotus "writes" (ζηνόδοτος γράφει...) vs. παρὰ Ζηνοδότῳ
![Critical Signs](https://raw.githubusercontent.com/mwauke/seniorThesis/master/Screen%20Shot%202017-04-21%20at%201.06.04%20AM.png)

---

## LDA Topic modeling

- Recurring patterns of co-occurring words
- User provides human-readable labels


---

## Significant topics for analysis

---


### Topic 6: Aristarchan language

![Topic 6](https://raw.githubusercontent.com/mwauke/seniorThesis/master/Topic06.png)

---

### Topic 9: Language used when discussing Aristarchus

![Topic 9](https://raw.githubusercontent.com/mwauke/seniorThesis/master/Topic09.png)

---

## Topic modeling scores

- Each scholion has a score for each topic, ranging 0-1

---

## Features for analysis

### Aristarchan features
- Critical sign
- γράφει (active)
- Initial ὅτι
- Higher topic 6 score

### Post-Aristarchan features

- παρὰ Ζηνοδότῳ
- Aristarchus' name
- Post-Aristarchan name
- Higher topic 9 score


---

## Features across three zones

|                          | Main | Intermarginal | Interior |
|:-------------------------|:-----|:--------------|:---------|
| Total # of scholia       | 3591 | 1217          | 818      |
| Aristarchus              | 272  | 236           | 105      |
| Post Aristarchan Name    | 238  | 14            | 12       |


---

## Manually classify a training set of 100 scholia

- Aristarchan
- Aristarchan paraphrase
- post-Aristarchan
- indeterminate

---

## Decision tree model trained from manually labeled set

- 5 levels deep
- 25 nodes

----

    If (feature 2 <= 0.0)
      If (feature 4 <= 0.0)
        If (feature 5 <= 0.0)

         If (feature 4 <= 0.0)
        If (feature 5 <= 0.0)


         If (feature 7 <= 0.00606060606060606)
          If (feature 7 <= 0.00103626943005181)
           Predict: 3.0
          Else (feature 7 > 0.00103626943005181)
           Predict: 4.0
         Else (feature 7 > 0.00606060606060606)
          If (feature 6 <= 0.00122699386503067)
           Predict: 2.0
          Else (feature 6 > 0.00122699386503067)

      ...

----


## Experimenting with the feature set

- including zone of scholion *lowered* success rate

---



## Applying the model

- evaluate model: success against training set (up to 90%)
- can then apply to remaining 7900+ scholia


---


## Results


- Main scholia contain the majority of Aristarchan and post-Aristarchan language
- Intermarginal and Interior scholia contain more Aristarchan paraphrases
- Zones are *not* the best feature for indicating source

---

## Future directions

- expand training set
- apply to expanded edition of Venetus A
- try alternate machine learning algorithms
- content analysis of automatically identified classes?

---


## Conclusions

- Analyze scholia as a collection of various features
- Working Aristarchan identifier:
    - Possible to recover material directly from Aristarchus' editions (2nd century BCE)



---

## Acknowledgements

- Professor Neel Smith, adviser
- Charlie Schufreider '17, collaborator
- Claude Hanley '18, research partner
- Professor Thomas Köntges, developer of ToPan Topic modeling software
- Holy Cross Department of Classics


---




---

## Features across three zones

|                          | Main | Intermarginal | Interior |
|:-------------------------|:-----|:--------------|:---------|
| Total # of scholia       | 3591 | 1217          | 818      |
| Critical Sign            | 1562 | 482           | 345      |
| γράφει                   | 174  | 26            | 12       |
| Initial ὅτι              | 885  | 234           | 183      |
| παρα Ζηνοδότῳ            | 12   | 7             | 3        |
| Aristarchus              | 272  | 236           | 105      |
| Post Aristarchan Name    | 238  | 14            | 12       |
| All Aristarchan Features | 113  | 14            | 9        |
| Topic 6 > .5             | 253  | 87            | 58       |
| Topic 6 > .9             | 74   | 28            | 16       |
| Topic 9 > .5             | 127  | 213           | 112      |
| Topic 9 > .9             | 44   | 64            | 28       |
