package com.chemistry.sections;

import java.util.Comparator;

import com.chemistry.questions.Question;

public class NumberModel implements Comparator<Question>{

	public int compare(Question question1, Question question2) {
		int i = Integer.parseInt(question1.getSection().substring(3, question1.getSection().length()));
		int j = Integer.parseInt(question2.getSection().substring(3, question2.getSection().length()));
		if(i < j)
			return -1;
		else
			return 1;
	}

}
