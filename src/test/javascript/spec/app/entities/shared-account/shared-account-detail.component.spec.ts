/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoolPassTestModule } from '../../../test.module';
import { SharedAccountDetailComponent } from 'app/entities/shared-account/shared-account-detail.component';
import { SharedAccount } from 'app/shared/model/shared-account.model';

describe('Component Tests', () => {
    describe('SharedAccount Management Detail Component', () => {
        let comp: SharedAccountDetailComponent;
        let fixture: ComponentFixture<SharedAccountDetailComponent>;
        const route = ({ data: of({ sharedAccount: new SharedAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoolPassTestModule],
                declarations: [SharedAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SharedAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SharedAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sharedAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
