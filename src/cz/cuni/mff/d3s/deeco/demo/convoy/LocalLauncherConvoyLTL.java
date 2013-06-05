package cz.cuni.mff.d3s.deeco.demo.convoy;

import java.util.Arrays;
import java.util.List;

import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeManager;
import cz.cuni.mff.d3s.deeco.knowledge.RepositoryKnowledgeManager;
import cz.cuni.mff.d3s.deeco.knowledge.local.KnowledgeJPF;
import cz.cuni.mff.d3s.deeco.knowledge.local.LocalKnowledgeRepositoryJPF;
import cz.cuni.mff.d3s.deeco.provider.AbstractDEECoObjectProvider;
import cz.cuni.mff.d3s.deeco.provider.PreLauncherDEECoObjectProvider;
import cz.cuni.mff.d3s.deeco.runtime.Runtime;
import cz.cuni.mff.d3s.deeco.scheduling.MultithreadedSchedulerJPF;
import cz.cuni.mff.d3s.deeco.scheduling.Scheduler;
import cz.cuni.mff.d3s.deeco.ltl.AtomicProposition;


public class LocalLauncherConvoyLTL 
{
	public static void main(String[] args) 
	{
		List<AtomicProposition> propositions = Arrays.asList(new AtomicProposition[] {
				new AtomicProposition() {					
					@Override
					public String getName() {
						return "isFollowerAtDestination";
					}
					
					@Override
					public Boolean evaluate(KnowledgeJPF knowledge) {
						return knowledge.getSingle("follower.position.x").equals(knowledge.getSingle("follower.destination.x"))
								&& knowledge.getSingle("follower.position.y").equals(knowledge.getSingle("follower.destination.y"));
					}
				},
				new AtomicProposition() {					
					@Override
					public String getName() {
						return "isFollowerNearLeader";
					}
					
					@Override
					public Boolean evaluate(KnowledgeJPF knowledge) {
						return knowledge.getSingle("follower.position.x").equals(knowledge.getSingle("follower.destination.x"))
								&& knowledge.getSingle("follower.position.y").equals(knowledge.getSingle("follower.destination.y"));
					}
				}

			});
		
		KnowledgeManager km = new RepositoryKnowledgeManager(new LocalKnowledgeRepositoryJPF(propositions));
		
		Scheduler scheduler = new MultithreadedSchedulerJPF();
		
		AbstractDEECoObjectProvider dop = new PreLauncherDEECoObjectProvider();
		
		Runtime rt = new Runtime(km, scheduler);
		rt.registerComponentsAndEnsembles(dop);
		rt.startRuntime();
	}
}
