First step for running a topic model with ToPan was to have a .tsv file. ToPan supports a number of different formats for data entry, but honestly, a tab-separated file was the only one we felt comfortable with.


The tab-separated file consists of two columns. The first has the urn of a particular scholion, while the second has a lemmatized version of that scholion.


The settings for data entry are fairly straightforward. There is no header, it is, again, a tab-separated file, and there are no quotes. Having submitted this, the text appeared as it was expected on the right part of the ToPan interface.


We skipped over the morphology service since we were working with an already lemmatized version of the text.


For the stop words, we had to do considerable work here. Although the text was lemmatized, due to the nature of our dataset, not every word could be properly lemmatized. Additionally, some Greek words can be validly parsed in mulitple ways, all of which are represented in our lemmatized edition. As a result, our dataset is fairly messy.


According to Thomas Koentges, the standard procedure for creating a stop-word list is to take out some number of the most frequently occurring words. The idea behind this is that the most common words (e.g. in English: the, a, and, etc.) are not useful for identifying topics. We used our own word frequency script to find the most common words in the scholia. While ToPan has a feature that can do this for you, using it is slightly tedious. After looking at the results of our frequency script, we decided to cap our stop-word list at the most frequently occurring 251 words. We chose this number because after this point, the words generally seemed to be significant for topic modelling. A significant word is one which is more than a function word; thus, significant words are typically verbs and nouns.


The next step, assuming every one of the 251 most common words was insignificant for topic modelling, would be to enter this data into ToPan, which lets you define how many of the most common words in a corpus should be excluded. However, there were some significant words contained in the 251 most common. Again, ToPan includes a feature which lets you record which words within your defined stop-word list should actually be excluded and therefore be part of the topic modelling analysis. But, the feature is still rather cumbersome to use within ToPan. As a result, we simply looked at our own list of most frequently occurring words from before, and recorded the significant words in a separate text file.


As stated before, however, this data was very messy. The task of recording which words were significant was NOT as simple as asking if the word was significant or not. Because a particular form of a Greek word, if taken out of context, could ostensibly be from a number of different words, our lemmatized text contained more words than actually appear in the manuscript. Sometimes these extra words are harmless, such as a form of τις always being parsed as τις and τίς since both τις and τίς are insignificant for topic modelling and appear frequently enough to be caught in our requirements for stop-words. Other times the extra words were not so innocuous. For example, the 74th most common word in the corpus of Greek scholia is apparently πηρός, an adjective referring to lameness in a leg. While it is possible that πηρός appears occasionally in the scholia as perhaps a word conatined in a quote from the *Iliad*, it is highly doubtful that it should be so common that it appears more than the preposition ὑπό. What appears to have happened is that the actually common preposition παρά can also be parsed as the Doric feminine singular nominative, as well as the Doric neuter plural nominative and accusative forms of πηρός. Thus πηρός, although a word that is not a function word, has to be excluded from analysis since it does not actually belong in the scholia.


Similarly, a word might be parsed in two ways but have essentially equivalent lemmata. For example the 73rd most common word in the scholia was χράω, while the 235th most common word was χράομαι. We decided that because these words were essentially the same, it was only necessary to include one in the analysis. For this instance, and others like it, we chose the more frequently occurring word as the one to include for topic modelling.


Finally, there was the matter of abbreviations and expansions. The name Aristarchus appeared multiple times within the list, as Ἀρίσταρχος (31st most common), ἀρίσταρχος (32nd most common), and Ἀρισταρχ (164th most common), to name a few. In a similar fashion to how we dealt with the previous case of words that had essentially equivalent lemmata, we chose to include the most frequently occurring expansion.


The final confounding factor of these stop-word lists, and the data in general, is the presence of Byzantine orthographic variants. At this stage in the project, we have no way of aligning the Byzantine orthographic variants with their normalized counterparts. Again, this can be harmless. δέ is the 7th most common word in the scholia, while δε is the 46th most common word. Although δε is undoubtedly the same word as δέ, the computer has no way of knowing this since the literal strings are not the same. In this case, however, both are function words and very common so both are excluded from analysis. This becomes an issue in instances where words do not appear frequently enough to appear in the 251 most common words, but common enough to make an impact on topic modelling. There is a collection of Byzantine orthographic variants and their normalized counterparts as part of the Homer Multitext project, but as of yet there has been no effort to make use of this collection. Thus, for right now, we just have to accept that our data is not the cleanest. Should we need to take another step in refining our dataset, we believe that this is the next in line. That is, incorporate the collection of Byzantine orthographic variants so as to eliminate the ambiguities in our dataset.


Finally, with our stop word list as prepared as possible, we were ready to run a topic model. 


The words we chose to save from the stopword list were: 


εὑρίσκω,Ἀσκαλωνίτης,ἄλλος,ἕτερος,Ἀρίσταρχος,γράφω,Ζηνόδοτος,λόγος,Ζεύς,ἑξῆς,ποιέω,γράφος,καλέω,δύναμαι,θεός,ποιητής,ὅμοιος,στίχος,ναῦς,βραχύς,μόνος,πρῶτος,μέγας,πόλις,σημαίνω,Τρώς,ἀθετέω,χράω,λαμβάνω,διασταλτέον,τόπος,ὅμηρος,δέω,θεάω,παῖς,ὁμοιόω,Ἀχιλλεύς,ἀναγιγνώσκω,στείχω,κεῖμαι,ὄνομα,πόλεμος,τέλος,Ἀριστοφάνης,ἀνήρ,ὀξύνω,ἵππος,πρόθεσις,ψιλόω,σύνδεσμος,μέρος,ἔρχομαι,τόνος,νέος,μέσος,ἕκτωρ,πείθω,χωρίς,κοινός,λέξις,γῆ,Γαῖα,διπλόος,δεύτερος,πρότερος,χρόνος,ἵστημι,ἐπιφέρω,φέρω,δίδωμι,κακός,ῥῆμα,τίθημι,βαρύνω,ἀκούω,μάχομαι,ἄγω,Ἕλλην,φεύγω,κύριος,Ὀδυσσεύς,πλείων,συνάπτω,ἀρχή,ἴσος,Ἀχαιός,βούλομαι,ταχύς,υἱός,δασύνω,βάλλω,δοκέω,στικτέον,μάχη,λήγω,λείπω,τεῖχος,θεά,τάσσω,ἴδιος,ἐθέλω,περισπάω


According to Thomas Koentges, the first time a topic model is run, the number of topic chosen is arbitrary - so choose either 10 or 25 topics and then compare results. We chose 10 topics. The only other variable we changed from the default ToPan settings was the number of iterations which we changed to 2000. After that we ran the topic model and we were able to look at the visualization - the results were extremely satisfying.


For reasons we do not fully understand, Thomas Koentges told us to set the lambda value to about 0.6. We think it makes the results easier to read.


Topic 1: A grammar topic.With a heavy emphasis on accentuation and breathing. 
Topic 2: War and heroes, with some evil vocabulary thrown in (Achilles,Phoenix) - this one could be clearer
Topic 3: Gods and geneology (Zeus, son, mother, Ares, etc.) - this one was fairly clear. Unsure why διωκω and φυγη.
Topic 4: Athetesis, Odyssey, line, 3, here, Zenodotos. Unsure about ἰδ-root words
Topic 5: This one is not very clear. There are lots of words meaning Troy and city. This might be a “troy” topic, but there are words like πλεω καλεω αδελφος θυγατηρ which make it difficult to classify.
Topic 6: Another grammar topic, with an emphasis on punctuation (διασταλτέον, στικτέον)
Topic 7: Editor’s topic. Lots of words related to γραφω and the names of the three editors. Other editor-related words: διχως χωρις
Topic 8: This is a mixed topic. There are a lot of “element” words like γαια ανεμος λιθος υδωρ αηρ χρυσεος νεφος. But there is also Olympus, ουρανος. So not people: elements and the heavens.
Topic 9: I cannot discern a clear topic. There are a lot of different forms for κυριος επιτιθημι βοη.  
Topic 10: Settings for battle topic? Lots of words for ships, plains, walls, and battle. Achaeans, ditch.


Overall there was about 7 of the 10 topics which were relatively clear: Topics 1,3,4,6,7,8, and 10.


Thus 2,5 and 9 could have been much clearer. Perhaps having more topics will help unpack some of these topics.


With a topic model complete for 10 topics, and with it clear that perhaps more topics might be needed, we re-ran the topic model with 


Topic 1: This is a FANTASTIC topic! It is a grammar topic, focusing mostly on accent.
Topic 2: Relationship and some gods - perhaps a geneology scholia topic (ιστορεω)
Topic 3: Confusing. The strongest word is αθετεω, but the majority of the words of names of heroes! That said, ψευδος αναφορα αστερισκοσ are also strong words. So while largely hero based, we cannot came it as solely hero.
Topic 4: More general grammar. Mostly seems to be parts of speech. Preposition, conjunction, pronoun, enclitic, case, tense (χρονος)
Topic 5: not a clear topic. There are some abstract, soul word (κακος θυμος ψυχη) so people (ανθρωπος) and what makes up people (δαιμων)
Topic 6: Very clear topic on punctuation words!
Topic 7: earth fire air cloud Olympus. As well as Ida, gold Cronus, Zeus, Gaia. So it seems like elements as well as some of the Titans? This isn’t entirely clear.
Topic 8: Same as topic 9 from the previous topic model, except words for war have replaced κυριος.
Topic 9: Still an editor’s topic very similar to Topic 7 from the 10-topic topic model.
Topic 10: Nothing clear here. Δεω is extremely strong here, along with Ares, the Odyssey, eat, light, fear, sleep, oppose. No idea.
Topic 11: Same as topic 10 from 10-topic topic model.
Topic 12: Troy topic. Much clearer than the Troy topic of the previous topic model. Troy, Paris and Helen.
Topic 13: Hand-to-hand combat. Blood, black, spear, lance, shield, blow, chest, bow, helmet, bronzen. Κυριος is the most frequent word for some reason.
Topic 14: Editor topic, but no Aristarchus. Just γραφω, αγνοεω, αριστοφανης ζηνοδοτος.
Topic 15: Just an Aristarchus topic. Ψιλοω χωρις φωνη δασυνω.


Of the 15 topics, topics 1,2,4,6,9,11,12,13,14,15 were fairly clear in their distribution. 


However, topics 3,5,7,8, and 10 were not. So while we gained three more topics over what we had in the 10-topic topic model, perhaps we are beginning to spread things too thin? We will try again at both 13 and 18 topics.

With the k-value set to 13, we ran a topic model and got the following topics:

Topic 1: Accents, with Ascalonites and Aristarchus
Topic 2: Family, geneology, birth-words, gods (Zeus, Apollo, Heracles), pol-root words, and Ilium words.
Topic 3: θε-root words, no real clear uniting factor. Looks a little more like a topic with the lambda set to 0.4: day, sit, watch, home-related words, δόξα. Still not very coherent.
Topic 4: Punctuation
Topic 5: Again, set λ to 0.4: Ares, battle-words, opposition/arguing over the text words (ἀθετέω, ἀντιόομαι, ψεῦδος,) 
Topic 6: Odyssey, heroes (Patroclus, Nestor, Paris), τιμη, emotions/seat of emotions (θυμος, ψυχη, στηθος)
Topic 7: χράω, υστερον-προτερον, best, Phoenix, Ajax (Iliad 9 scholia?), again, not the clearest topic
Topic 8: γραφω, Zenodotus, Aristophanes, Aristarchus, γραφεται, athetesis, siege vocab (tower, missile, stone, wood)?
Topic 9: Scenery/back-drop words (earth, wind, water, fire), Olympus, height, sacrifice, hollow, sea
Topic 10: Another grammar topic, focusing on verbs and breathing (tense, aorist, imperative, negation), function words
Topic 11: Battle words, particularly quick battle (swift, shield, sword, arrow, blood, body), and metaphor?
Topic 12: Ships and sailing, epithets, shouting
Topic 13: Aristarchus! αλλος, χωρις, διχως, Also, plain and wall?

Overall, this one was the most disappointing of the topic models we have run. Clear topics include 1,4,9,10,11,12(?),13. There were several topics that had some clear parts, but then also seemingly unrelated words. For example, in topic 8, there was a clear grammatical/editors part, but then the siege vocabulary. Then there were topics like topic three, which had no clear pattern at all. Topic five is potentially promising, but we have not seen any topic like it before. 

Topic 1: general grammar: smooth breathing, similar, enclitics, parts of speech, pronouns, syntactical arrangement
Topic 2: Grammar, but more specifically accentuation: syllable, ought, accent, name, plural, fem/masc
Topic 3: City, familial relations (birthing and raising), sacking, ιστορέω
Topic 4: Gods and sacrifice: Zeus, Gaia, heaven, Olympus, Poseidon, gold, cloud, mother, Hermes, Apollo
Topic 5: Athetesis and the Odyssey: line, asterisk, Zenodotus, Hector, order, Paris, βουλη
Topic 6: punctuation
Topic 7: Heroes in battle? Patroclus, best, war, harm, take, destroy, other hero names, also home-words
Topic 8: δει, χρη, eat ( upon further reflection, εδω is probably actually δει), meet, epithet, first, second, Aristonicus. No clear topic here
Topic 9: Unclear again: θεαω, hear, νοος, φρην, some grammar words too (πτωσις).
Topic 10: Note: should add ποτε to stop-words list as it seems to be skewing results. fire, blow, sword, spear, απειλω,. Weapon and pursuit words, mostly.
Topic 11: Alexandrian editors: Aristarchus, Aristophanes, Zenodotus, others, γραφω, διχως
Topic 12: Ilium, Phoenix, Ajax, Muses, similarity, courage, victory, soul and heart. Not very clear. 
Topic 13: Battle scenery: Ship, fight, plain, wall, tower, wood, ditch
Topic 14: War, flight, dog, lion, fear, εναντιοομαι, manly, foreign, (Possibly simile?)
Topic 15: note: πλε- seems to be causing confusion. Junk category: την, τον, της, bronze, horse, death, white, Helen, woman
Topic 16: Ares, day, plain, river, new/newer (νεωτεροι), helmet, sleep. Unclear. 
Topic 17: Zenodotus by himself: γραφω, ταχύς, αντι, Some unrelated words: πεπλος, wine
Topic 18: βο- causes confusion. Shouting, honor, find

18-topic topic model was better than 13, but still not great. The clear topics were 1-6,11,13,14(?),17
Just like with the 13-topic topic model, there were several that had a few related words, but with other random words thrown in. There were also some complete junk categories. 

Of all the topic models we have run, the 15-topic topic model has produced the best results. We could not put an exact label on every topic, but it was on the whole the most clear. It had just as many clear topics as 18, with less junk. 

Out of curiosity, we decided to see what would happen if we modified our stopword list so that it contained every Byzantine orthographic equivalent. We ran a 15-topic topic model with 2000 iterations using this new stopword list. These are the results:

Topic 1: Zeus and other gods, geneology
Topic 2: Accentuation 
Topic 3: Hero topic (Agamemnon, Odysseus, Achilles, Phoenix), quickly, god, αλλος, poet, ancient
Topic 4: Athetesis, arrangement, lines, conjunction, false, better
Topic 5: More grammar, talking about individual words (similar, preposition, pronoun, conjunction, Ascalonites, case, enclitic)
Topic 6: Editors (γραφω, Zenodotus, Aristarchus, Aristophanes, χωρις, διχως, αθετεω)
Topic 7: Troy and battle (Patroclus, Paris, Sarpedon, war, fight, Pandarus, Ida)
Topic 8: Setting + Odyssey (Gaia, earth, heaven, cloud, water, air, light, road, Odyssey) 
Topic 9: Perception (ψυχη, λυπεω, λυπη, θυμος, καρ κεφαλη, δηλοω, νομιζω, θυμοω), with some other less related words (house, black, Helen, seven)
Topic 10: Weird mix of grammar and Iliad (ship, διασταλτεον, ταφρος, βραχυς, τειχος)
Topic 11: Unclear topic (Plain, fleeing, fear, wall, horse, Troy, city, but also νεωτερος appears almost entirely in this topic)
Topic 12: Even more grammar, but also unrelated words (Breathings, Odyssey, peplos, negation, Arrow)
Topic 13: Perhaps comments on similes? Not too clear though. lots of nature. (δοκεω, φυσις, φυω, κυων, αλεξω). 
Topic 14: Junk, probably. (Epithets, δει, κυριως, meet, persuade, first, second, names) 
Topic 15: Weapons and shouting (shield, spear, honor, sword, gold, helmet, bronze)

Thoughts: 1-7 are fine, 8 and 9 are murky. 15 is good. Compared to the first 15-topic topic model we ran, this analysis is less valuable. There is only one editors topic here, as opposed to the three distinct editors topics in the previous topic model we ran. 

Due to the ToPan update, we decided to rerun our 15-topic topic model and see what happened. Initially, we wanted to run the topic model in scala using a Sparknotebook, but this proved to be a deadend. The spark / scala server demanded more memory and our request seemed to crash the server. For now, the only solution seems to rest on paring down our corpus so that it is much more manageable. As of right now problems with our parsing lead to issues with corpus management that seem to overpower the scala/spark server. Thus we had to return to ToPan. 

Being fairly confident from before, we knew that 15 topics were a good amount to begin our analysis. Also, being more confident we were less concerned with time, so we decided to run the maximum number of iterations on ToPan - 5000. Rather than detail all fifteen topics it is easier to note that this topic model was unsatisfactory. Very quickly it became clear that this topic model was weaker than past ones. There were, as usual three topics devoted to grammatical vocabulary. Another topic was devoted just battle vocabulary. But what was conspicuously missing were any clear editor topics as before. Aristarchus, Zenodotus, and Aristophanes were blatantly absent! Rather than asssume ToPan was broken, we decided to check our stopword list. Sure enough, this was where the issue lay. The new ToPan changes all the words in a corpus to lower case, something which the previous ToPan only sort of did. The previous ToPan would create two words for some uppercase words - one that was lower case and one that was upper case. The names of the three Alexandrian editors were three such words. So when we created our stopword list for the old ToPan, we made sure to remove the upper case version of these three names. However, these no longer exist in the corpus created by the new ToPan. Thus Aristarchus, Aristophanes, and Zenodotus were technically never removed from the ToPan stopword list, and thus were not counted in the topic model. So in sum, further topic models using ToPan will require us to readjust our stop word list to ensure that we are removing all the words we mean to and keeping all the words we mean to.
