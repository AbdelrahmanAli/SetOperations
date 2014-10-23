import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;


public class SetLogic 
{
	// universe at index 0
	public int lastSetIndex;
	public ArrayList<HashSet> subsets; // save all sets
	public Map<String,Integer> setNames; // save all set names with thier indices
	
	public SetLogic()
	{
		lastSetIndex = 0;
		subsets = new ArrayList();	
		setNames = new HashMap<String,Integer>();
	}
	
	
	public void addSet(String set,String setName)
	{
		
			String readySet[] = parseString(set);
			HashSet newSet = new HashSet();
			for (int i = 0; i < readySet.length; i++) 	newSet.add(readySet[i]);
			
			subsets.add(newSet); // add the set at the correct position
			setNames.put(setName,lastSetIndex); // add set name with it's index
			lastSetIndex++; // increment set counter
	}
	
	public String[] parseString(String set) // {1,2,3,4,5,6,7}
	{
		String readySet[];
		set = set.replace("{", "");
		set = set.replace("}", "");
		set = set.replaceAll(" ", "");
		set = set.replaceAll("\n", "");
		set = set.replaceAll("\t", "");
		
		if(set.contains(","))
		{
			readySet = set.split(",");
		}
		else
		{
			readySet = new String[1];
			readySet[0] = set;
		}
		return readySet;
	}
	
	public HashSet operations(String set1,String set2,int operation)
	{
		HashSet result = new HashSet();
		int set1Index,set2Index;
		switch(operation)
		{
			case 1: // intersection
					set1Index = setNames.get(set1);
					set2Index = setNames.get(set2);
					result = intersect(set1Index, set2Index);
					break;
					
			case 2: // union
					set1Index = setNames.get(set1);
					set2Index = setNames.get(set2);
					result = union(set1Index, set2Index);
					break;
					
			case 3: // 1st complement
					set1Index = setNames.get(set1);
					result = complement(set1Index);
					break;
				
			case 4: // 2nd complement
					set2Index = setNames.get(set2);
					result = complement(set2Index);
					break;
		}
		
		return result;
	}
	
	public HashSet intersect(int set1,int set2) //1
	{
		HashSet intersection = new HashSet();
		int count = 0;
		if(subsets.get(set1).size() < subsets.get(set2).size())
		{
			Iterator iterator = subsets.get(set1).iterator();
			while (iterator.hasNext()) 
			{
				String element =  iterator.next() + "";
				if( subsets.get(set2).contains( element ) )
				{
					intersection.add(element);
					count++;
				}
					
			}
		}
		else
		{
			Iterator iterator = subsets.get(set2).iterator();
			while (iterator.hasNext()) 
			{
				String element =  iterator.next() + "";
				if( subsets.get(set1).contains( element ) )
				{
					intersection.add(element);
					count++;
				}
			}
		}
		
		if(count==0) intersection.add("Phi");
		return intersection;
	}
	
	public HashSet union(int set1,int set2) //2
	{
		HashSet union = new HashSet();
		if(subsets.get(set1).size() < subsets.get(set2).size())
		{
			// copy the large set
			Iterator iterator = subsets.get(set2).iterator();
			while(iterator.hasNext())
			{
				String element =  iterator.next() + "";
				union.add(element);
			}
			
			//get the remaining items
			iterator = subsets.get(set1).iterator();
			while (iterator.hasNext()) 
			{
				String element =  iterator.next() + "";
				if( !subsets.get(set2).contains( element ) && !element.equalsIgnoreCase("Phi") )	union.add(element);
			}
		}
		else
		{
			// copy the large set
			Iterator iterator = subsets.get(set1).iterator();
			int count = 0;
			while(iterator.hasNext())
			{
				String element =  iterator.next() + "";
				if(subsets.get(set1).size()== 1 && subsets.get(set2).size()==1 && element.equalsIgnoreCase("Phi")) break;
				union.add(element);
				count++;
			}
			
			//get the remaining items
			iterator = subsets.get(set2).iterator();
			while (iterator.hasNext()) 
			{
				String element =  iterator.next() + "";
				if( !subsets.get(set1).contains( element )  && !element.equalsIgnoreCase("Phi") )
				{
					union.add(element);
					count++;
				}
			}
			
			if(count == 0) union.add("Phi");
		}
		return union;
	}
	
	public HashSet complement(int set) // 3 or 4
	{
		HashSet complement = new HashSet();
		if(subsets.get(0).size() == subsets.get(set).size())
		{
			complement.add("Phi");
		}
		else
		{
			Iterator iterator = subsets.get(0).iterator();
			while (iterator.hasNext()) 
			{
				String element =  iterator.next() + "";
				if( !subsets.get(set).contains( element ) && !element.equalsIgnoreCase("Phi") )
				{
					complement.add(element);
				}
			}
		}
		return complement;
	}
	
	public boolean validateOperation(String set1,String set2, int operation)
	{
		
		//universe set exist with at least 1 subset
		if(lastSetIndex<=1)
		{
			JOptionPane.showMessageDialog(null,"More data is needed.","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		
		switch(operation)
		{
			case 1: // intersection
					if(setNames.get(set1)==null || setNames.get(set2)==null)
					{
						JOptionPane.showMessageDialog(null,"Invalid Set Name!\nThe name of one of the sets doesn't exist.","Error Message",JOptionPane.ERROR_MESSAGE );
						return false;
					}
					break;
					
			case 2: // union
					if(setNames.get(set1)==null || setNames.get(set2)==null)
					{
						JOptionPane.showMessageDialog(null,"Invalid Set Name!\nThe name of one of the sets doesn't exist.","Error Message",JOptionPane.ERROR_MESSAGE );
						return false;
					}
					break;
					
			case 3: // 1st complement
					if(setNames.get(set1)==null)
					{
						JOptionPane.showMessageDialog(null,"Invalid Set Name!\nThe name of the set doesn't exist.","Error Message",JOptionPane.ERROR_MESSAGE );
						return false;
					}
					break;
				
			case 4: // 2nd complement
					if(setNames.get(set2)==null)
					{
						JOptionPane.showMessageDialog(null,"Invalid Set Name!\nThe name of the set doesn't exist.","Error Message",JOptionPane.ERROR_MESSAGE );
						return false;
					}
					break;
		}
		
		return true;
	}
	
	
	public boolean validate(String input, String name)
	{
		
		// there exist a name for the set
		if(name.length()==0)
		{
			JOptionPane.showMessageDialog(null,"Missing Set Name!"+"\nSet should have a unique name.","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		// there already a set with the same name
		if(setNames.get(name)!=null) 
		{
			JOptionPane.showMessageDialog(null,"Invalid Set Name: "+name+"\nThe name of this set already exist.","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
					
		
		// remove unnecessary chars
		input = input.replaceAll(" ", "");
		input = input.replaceAll("\n", "");
		input = input.replaceAll("\t", "");

		// phi 
		if(input.compareToIgnoreCase("Phi") == 0) return true;
		
		// invalid comma
		if( (input.charAt(0)=='{' && input.charAt(1)==',') || (input.charAt(input.length()-1)=='}' && input.charAt(input.length()-2)==','))
		{
			JOptionPane.showMessageDialog(null,"Invalid comma separators: "+input+"\nComma must separat 2 elements in the subset.","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		// check successive commas
		int firstComma = -1;
		for(int i = 0 ; i < input.length() ; i++)
		{
			if(firstComma==-1)
			{
				if(input.charAt(i)==',') firstComma = i;
			}
			else
			{
				if(input.charAt(i)==',')
				{
					if(i - firstComma == 1)
					{
						JOptionPane.showMessageDialog(null,"Invalid comma separators: "+input+"\nComma must separat 2 elements in the subset.","Error Message",JOptionPane.ERROR_MESSAGE );
						return false;
					}
					else
					{
						firstComma = i;
					}
				}
			}
		}
		
		//check length of string
		if(input.length()<=2)
		{
			JOptionPane.showMessageDialog(null,"Invalid Input length: "+input+"\nLength should be greater than 2 characters.","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		
		// check start and end bracket
		if(input.charAt(0)!='{' || input.charAt(input.length()-1)!='}')
		{
			JOptionPane.showMessageDialog(null,"Invalid # of Brackets: "+input+"\nThere must be brackets at the beginning & at the end of the input.","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		
		//check occurrence of more than 1 bracket
		int count = input.split("\\{").length;
		if(count!=2)
		{
			JOptionPane.showMessageDialog(null,"Invalid # of Brackets: "+input+"\nThere must exist only 1 of this bracket {","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		count = input.split("}").length;
		if(count!=1)
		{
			JOptionPane.showMessageDialog(null,"Invalid # of Brackets: "+input+"\nThere must exist only 1 of this bracket }","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		
		//phi can't be included in the set because it exist by default
		if(input.toLowerCase().contains("phi"))
		{
			JOptionPane.showMessageDialog(null,"Invalid Input: "+input+"\nPhi can't be included in a set as it exist in all the sets by default.","Error Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		
		if(lastSetIndex != 0) // if there is already a universe set
		{
			//	remove brackets
			input = input.replaceAll("\\{", "");
			input = input.replaceAll("}", "");
		
			//check if it's a subset or not
			if(input.contains(","))
			{
				String inputArray[] = input.split(",");
				for(int i = 0 ; i < inputArray.length ; i++)
				{
					if(!subsets.get(0).contains(inputArray[i]))
					{
						JOptionPane.showMessageDialog(null,"Invalid Subset: "+input+"\nThis set contains items doesn't exist in the universe set, So it's not a subset.","Error Message",JOptionPane.ERROR_MESSAGE );
						return false;
					}
				}
			}
			else
			{
				if(!subsets.get(0).contains(input))
				{
					JOptionPane.showMessageDialog(null,"Invalid Subset: "+input+"\nThis set contains items doesn't exist in the universe set, So it's not a subset.","Error Message",JOptionPane.ERROR_MESSAGE );
					return false;
				}
			}
		}
		
		return true;
	}
	


	public String setToString()
	{
		String str = "{";
		if(lastSetIndex==1) // universeSet
		{
			Iterator iterator = subsets.get(0).iterator();
			while (iterator.hasNext()) 
			{
				str +=  iterator.next() + ",";
				if(str.equalsIgnoreCase("{Phi,")) return "Phi";
			}
		}
		else
		{
			Iterator iterator = subsets.get(lastSetIndex-1).iterator();
			while (iterator.hasNext()) 
			{
				str +=  iterator.next() + ",";
				if(str.equalsIgnoreCase("{Phi,")) return "Phi";
			}
		}
		str = str.substring(0,str.length()-1);
		str+="}";
		
		return str;
	}
	
	public String setToString(HashSet set)
	{
		String str = "{";
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) 
		{
			str +=  iterator.next() + ",";
			if(str.equalsIgnoreCase("{Phi,")) return "Phi";
		}
		str = str.substring(0,str.length()-1);
		str+="}";
		
		return str;
	}
}
