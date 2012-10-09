e1-job-archive
==============

Test task #1 from http://xrm.ru/job/test_task/

Tools used:
IDE: eclipse 
+ m2e (maven for eclipse) 
+ eclemma (code coverage)
+ junit


Structure of data on http://www.e1.ru/business/job/ :
	
    Vacancy Section1
        + VacancyList page0
            + VacancyLink1
               - Vacancy1
            + VacancyLink2
               - Vacancy2
            ...
            + VacancyLinkN
               - VacancyN
        + VacancyList page1
        ...
        + VacancyList pageX
    
    Vacancy Section2
    ...
    Vacancy SectionN

see config.xml to understand relationship between ElementWalker, ElementEvaluator and PropertyTransformer